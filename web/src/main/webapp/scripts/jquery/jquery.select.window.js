
(function( $, undefined ) {
	
	/**
	 * 选择窗口
	 */
	$.widget("ui.selectWindow", {
		options: {
			textWidth : 200,
			resizable : false,
			title : null,
			cfgAttr : null,
			width : 300,
			height : 300,
			getSelectedData : function(data) {return data},
			okHandler : $.noop(),
			valueChange : $.noop()
		},
		// 在弹出框中选择的结果条
		resultbar : null,
		
		_create : function() {
			var self = this, options = self.options, uiValueField = (self.uiValueField = $('input:first-child', self.element)),
			id = (self.id = $(uiValueField).attr('id')), title = options.title || $(self.element).attr('title')
			// 文本，显示用户选择的值
			, uiInputText = ( self.uiInputText = $('<span></span>'))
			.addClass('pop_selection input-text').css('width', options.textWidth)
			.append( (uiInputTextIcon = (self.uiInputTextIcon = $('<em>&nbsp;</em>').addClass('icon_selection'))))
			// 修改按钮
			, editText = (self.editText = $(' <a href="#">修改</a>'))
			// 弹出框
			, dialog = (self.dialog = $('<div title="' + title + '"></div>'));
			uiValueField.attr(options.cfgAttr||{});
			uiValueField.hide();
			editText.hide();

			// 把各个对象增加到元素里
			$(self.element).before(uiInputText).before(editText).before(dialog);
			
			uiInputText.click(function(event){
				clickHandler(event);
				event.preventDefault();
			});
			editText.click(function(event){
				clickHandler(event);
				event.preventDefault();
			});
			
			function clickHandler(event) {
				$(dialog).dialog({
					modal : true,
					resizable : false,
					width : options.width,
					height : options.height,
					open : $.proxy(self.opened, self),
					buttons : [{
						text : '确定',
						click : $.proxy(self._okHandler, self)
					}, {
						text : '取消',
						click : $.proxy(self._cancelHandler, self)
					}]
				});

				self._setupContent(dialog);
			}
			
		},
		
		_init : function() {
			var self = this, options = self.options;
			var data = $.parseJSON(self.uiValueField.val()) || [];
			$.each(data, function (n, o) {
				self.addObj(o.id, o.text);
			});
		},
		
		_okHandler : function (event) {
			var val = [], self = this;
			self.removeAll();
			var data = $(self.resultbar).resultbar('getAllData') || {};
			$.each(data, function (n, o) {
				self.addObj(o.id, o.text);
			});
			$(self.dialog).dialog('close');
			if($.isFunction(self.options.okHandler)){
				self.options.okHandler(val);
			}
			if($.isFunction(self.options.valueChange)){
				self.options.valueChange(val);
			}
			event.preventDefault();
		},
		
		_addData : function(id, text) {
			// 遍历所有数据，取出已有的数据
			var self = this, data = $(self.element).data('itemData') || [];
			data.push({
				id : id,
				text : text
			});
			$(self.element).data('itemData', data);
			data = self.options.getSelectedData(data);
			self.initValueField(self._objToJsonSting(data));
		},
		
		_removeData : function(id) {
			var self = this, itemData = $(self.element).data('itemData');
			if ($.isArray(itemData)) {
				itemData = $.grep(itemData, function (o, i) {
					return o.id != id;
				});
				$(self.element).data('itemData', itemData);
			}
		},

		// 对象转换为json字符串
		_objToJsonSting : function(array) {
			var strVal = '';
			$(array).each(function() {
				if (strVal != '') {
					strVal += ', ';
				}
				strVal += '{"id":"' + $.trim(this.id) + '", "text":"' + $.trim(this.text) + '"}';
			});
			if (strVal != '') {
				strVal = '[' + strVal + ']';
			}
			return strVal;
		},

		// 重置文本框
		_resetUiInputText : function(b) {
			var self = this;
			if (b) {
				self.editText.show();
				self.uiInputTextIcon.hide();
			} else {
				self.editText.hide();  // 隐藏编辑按钮
				self.uiInputTextIcon.show();
			}
		},
		
		_cancelHandler : function (event) {
			var self = this;
			event.preventDefault();
			$(self.dialog).dialog('close');
		},

		_setupContent : function (dialog) {},
		
		getAllData : function() {
			var self = this;
			return $(self.element).data('itemData');
		},
		
		addObj : function(id, text) {
			id = id+'';
			var self = this, labeltmpl = '<label for="${id}">${text}</label>'
				, icontmpl = '<img id="${id}" src="' + ctx + '/images/close.gif" />'
				, label = $($.tmpl(labeltmpl, {id: id, text: text}))
				, closeIcon = $($.tmpl(icontmpl, {id: id}));
			label.append(closeIcon);

			var elements = $(self.element).data('elements') || [] ;
			elements.push(label);
			$(self.element).data('elements', elements);
			$(self.uiInputText).append(label);
			self._addData(id, text);
			self._resetUiInputText(true);
			// 处理事件
			closeIcon.click(function(event) {
				self.removeObj(label);
				self._trigger('remove', event, {icon : closeIcon, label : label});
				event.stopPropagation();
			});
			label.click(function(event) {
				// 单击label
				self._trigger('labelclick', event, {icon : closeIcon, label : label});
				event.stopPropagation();
			});
			return label;
		},
		
		removeAll : function() {
			var self = this;
			$($(self.element).data('elements')).each(function(index, obj) {
				self.removeObj(obj);
			});
		},

		// 移除标签
		removeObj : function(label) {
			var self = this, val = [];
			label.remove();
			self._removeData($(label).attr('for'));
			// 更新文本框中的json串
			$.each(self.getAllData(), function (n, o) {
				var item = {};
				item.id = o.id;
				item.text = o.text;
				val.push(item);
			});
			val = self.options.getSelectedData(val);
			self.initValueField(self._objToJsonSting(val));
			if($.isFunction(self.options.valueChange)){
				self.options.valueChange(val);
			}
			if (self.getAllData().length <= 0) {
				self._resetUiInputText(false);
			}
		},
		
		// 初始化字段数据
		initValueField : function(strVal) {
			var self = this;
			$(self.uiValueField).val(strVal);
		},
		
		opened : function() {
			
		}
	});
	
	$.extend($.ui.selectWindow, {
		version: "1.8.20"
		
	});
	
	/**
	 * 选择结果工具条
	 */
	$.widget('ui.resultbar', {
		options: {
			maxsize : 3
		},
		
		length : 0,
		
		_create: function() {
			$(this.element).addClass('Pop-top Clear').append('<strong class="fl">已选择：</strong><ul></ul>');
			
		},
		
		_init : function() {
			var self = this;
			$('em', self.element).live('click', function(event){
				var id = $(event.target).parent().attr('id');
				self.removeItem(id);
				self._trigger('close', event, id);
				event.stopPropagation();
			});
		},
		
		size : function() {
			return this.length;
		},
		
		addItem : function(key, object) {
			key = key +'';
			var self = this, options = self.options;
			if (!$(self.element).data(key)) {
				$('ul', self.element).append($.tmpl('<li id="${id}">${text}<em>&nbsp;</em></li>', {
					'text' : object.text, 
					'id' : key
				}));
				self.setData(object);
				self.length += 1;
			}
			self.checkSize();
		},
		
		removeItem : function (key) {
			var self = this, options = self.options;
			$('li[id=' + key + ']', self.element).remove();
			self.removeData(key);
			self.length = self.length - 1;
			self.checkSize();
			self._trigger('remove', self, key);
		},
		
		checkSize : function () {
			var self = this, options = self.options;
			if (self.length >= options.maxsize) {
				self._trigger('overflow', self);
			} else {
				self._trigger('notOverflow', self);
			}
		},

		removeAll : function () {
			var self = this;
			$(self.getAllData()).each(function(index, obj){
				self.removeItem(obj.id);
			});
		},
		
		setData : function (data) {
			var self = this, itemData = self.getAllData();
			if ($.isArray(itemData)) {
				itemData = $.grep(itemData, function (o, i) {
					return o.id != data.id;
				});
			} else {
				itemData = new Array();
			}
			itemData.push(data);
			$(self.element).data('itemData', itemData);
		},
		
		removeData : function (id) {
			var self = this, itemData = self.getAllData();
			if ($.isArray(itemData)) {
				itemData = $.grep(itemData, function (o, i) {
					return o.id != id;
				});
				$(self.element).data('itemData', itemData);
			}
		},
		
		getData : function (id) {
			var self = this, itemData = self.getAllData();
			if ($.isArray(itemData)) {
				$(itemData).each(function(index, obj) {
					if (obj.id == id) {
						return obj;
					}
				});
			}
		},
		
		getAllData : function() {
			var self = this, itemData = $(self.element).data('itemData') || [];
			return itemData;
		}
	});
	
	/**
	 * 可选项
	 */
	$.widget('ui.eOption', {
		options: {
			tmpl : null,
			itemData : {},
			checked : false
		}, 
		
		_create: function() {
			var self = this, options = self.options, itemData = options.itemData;
			options.tmpl = (options.tmpl == null) ? '<label for="${id}"><input id="${id}" name="eOption" type="${type}" value="${value}" />&nbsp;${text}</label>' : options.tmpl;
			$(self.element).append($.tmpl(options.tmpl, itemData));
			$(self).data(itemData.id, itemData);
			$('input', self.element).click(function(event) {
				var checked = $(event.currentTarget).prop('checked');
				self._trigger('click', event, {data : $(self).data(itemData.id), self : self, checked : checked});
			});
			
			$('input', self.element).prop('checked', self.checked);
		},
		
		setDisabled : function(b) {
			$('input', self.element).prop('disabled', b);
		}
	});
	
	/**
	 * 选项面板
	 */
	$.widget('ui.optionsPanel', {
		options: {
			type : 'multi',     // single, multi
			url : null,
			tmpl : null
		},
		
		size : 0,
		
		_create: function() {
			var self = this, options = self.options;
			$.ajax(options.url, {
				success : createHandler,
				dataType : 'json'
			});
			
			function createHandler(data) {
				$(self.element).append('<div><ul></ul></div>');
				$(data).each(function(n, e) {
					var itemData = {
						id : e.id,
						text : e.text,
						value : e.id,
						type : options.type == 'multi' ? 'checkbox' : 'radio'
					};
					self.addItem(itemData);
				});
				self._trigger('created', self);
			}
		},
		
		addItem : function(data) {
			var self = this;
			var option = $('<li></li>').eOption({
				itemData : data,
				click : function(event, options) {
					options.panel = self;
					self._trigger('click', event, options);
				}
			});
			$('ul', self.element).append(option);
			self.size += 1;
			
			var itemData = $(self.element).data('itemData');
			if ($.isArray(itemData)) {
				$.grep(itemData, function (o, i) {
					return o.id != data.id;
				});
			} else {
				itemData = new Array();
			}
			itemData.push(data);
			$(self.element).data('itemData', itemData);
		},
		
		disabledChecked : function(disabledStatus) {
			$(':checked', this.element).prop('disabled', disabledStatus);
		},
		
		disabledNotChecked : function(disabledStatus) {
			$('input:not(:checked)', this.element).prop('disabled', disabledStatus);
		},
		
		disabledAll : function(disabledStatus) {
			$('input', this.element).prop('disabled', disabledStatus);
		},
		
		checked : function(id, status) {
			$('input[value=' + id + ']', this.element).prop('checked', status);
		}
	});
	
	/**
	 * 列表导航
	 */
	$.widget("ui.listnav", $.ui.selectable, {
		options : {
			tolerance : 'fit',
			url : null
		},
		
		_create : function () {
			var self = this, options = self.options;
			$.ajax(options.url, {
				success : createHandler,
				dataType : 'json',
				async : false
			});
			$(self.element).attr('id', 'selectable');
			
			function createHandler(data) {
				$(data).each(function(i, obj) {
					var content = $.tmpl('<li id="${id}" class="ui-widget-content">${text}</li>', {id : obj.id, text : obj.text});
					$(self.element).append(content);
				});
			}
			$.ui.selectable.prototype._create.call(self);
		}
	});

	/**
	 * 地区选择
	 */
	$.widget("ui.selectRegion", $.ui.selectWindow, {
		options : {
			width : 680,
			height : 500,
			type : 'multi', // single, multi
			rootId : '100',
			rootName : '全部'
		},
		
		_setupContent : function(dialog) {
			var self = this, options = self.options
			, mainPanel = $('<div class="pop-selectRegion Clear"></div>').optionsPanel({
				url : ctx + '/ajax/regions?id=' + options.rootId, 
				click : optionClick,
				created : mainPanelCreated,
				type : options.type
			});
			self.resultbar = $('<div></div>').resultbar({overflow : resultbarOverflow, notOverflow : resultbarNotOverflow, remove : removeHandler });
			$(dialog).empty().append(self.resultbar).append(mainPanel);

			function mainPanelCreated($this) {
				$($.parseJSON($(self.uiValueField).val())).each(function (i, obj) {  // 初始化已有的值
					$(mainPanel).optionsPanel('checked', obj.id, true);
					$(self.resultbar).resultbar('addItem', obj.id, obj);
				});
				var itemData = {
					id : options.rootId,
					text : options.rootName,
					value : options.rootId,
					type : options.type == 'multi' ? 'checkbox' : 'radio'
				};
				// "全部"选项
				var allOption = $('<div class="pop-data-all"></div>').eOption({
					itemData : itemData,
					click : function(event, opt) {
						$(self.resultbar).resultbar('removeAll');
						$(mainPanel).optionsPanel('disabledAll', opt.checked);
						if (opt.checked) {
							$(self.resultbar).resultbar('addItem', opt.data.id, opt.data);
						}
					}
				});
				$(mainPanel).before(allOption);
			}
			
			function resultbarOverflow($this) { // 结果集大小超出最大限额
				$(mainPanel).optionsPanel('disabledNotChecked', true);
			}
			
			function resultbarNotOverflow($this) {  // 结果集大小没有超出最大限额
				if ($('li', $this.target).attr('id') != options.rootId) {
					$(mainPanel).optionsPanel('disabledAll', false);
				}
			}
			
			function removeHandler(event, id) {
				$(mainPanel).optionsPanel('checked', id, false);
			}
			
			function optionClick(event, options) {  // 选项面板的单击事件
				if (self.options.type == 'single') {
					$(self.resultbar).resultbar('removeAll');
					$(self.resultbar).resultbar('addItem', options.data.id, options.data);
				} else {
					if (options.checked) {
						$(self.resultbar).resultbar('addItem', options.data.id, options.data);
					} else {
						$(self.resultbar).resultbar('removeItem', options.data.id);
					}
				}
			}
		}
	});
	
	/**
	 * 行业选择
	 */
	$.widget("ui.selectIndustry", $.ui.selectWindow, {
		options : {
			width : 560,
			height : 500,
			type : 'multi', // single, multi
			level : 'one', // all, last
			rootId : '0',
			rootName : '不限'
		},
		
		_setupContent : function(dialog) {
			var self = this, options = self.options;
			if (options.level == 'one') {
				self._oneAndLast(dialog, ctx + '/ajax/industrys?id=' + options.rootId);
			} else if (options.level == 'last') {
				self._oneAndLast(dialog, ctx + '/ajax/industrys!getTheJsonLastNodeIndustrys');
			} else {
				self._all(dialog);
			}
		}, 
		
		_oneAndLast : function(dialog, url) {
			var self = this, options = self.options
			, mainPanel = $('<div class="pop-selectIndustry Clear"></div>').optionsPanel({
				url : url, 
				click :  $.proxy(self.optionClick, self),
				created : mainPanelCreated,
				type : options.type
			});
			self.resultbar = $('<div></div>').resultbar({overflow : resultbarOverflow, notOverflow : resultbarNotOverflow, remove : removeHandler });
			$(dialog).empty().append(self.resultbar).append(mainPanel);

			function mainPanelCreated($this) {
				$($.parseJSON($(self.uiValueField).val())).each(function (i, obj) {  // 初始化已有的值
					$(mainPanel).optionsPanel('checked', obj.id, true);
					$(self.resultbar).resultbar('addItem', obj.id, obj);
				});
				var itemData = {
					id : options.rootId,
					text : options.rootName,
					value : options.rootId,
					type : options.type == 'multi' ? 'checkbox' : 'radio'
				};
				// "全部"选项
				var allOption = $('<div class="pop-data-all"></div>').eOption({
					itemData : itemData,
					click : function(event, opt) {
						$(self.resultbar).resultbar('removeAll');
						$(mainPanel).optionsPanel('disabledAll', opt.checked);
						if (opt.checked) {
							$(self.resultbar).resultbar('addItem', opt.data.id, opt.data);
						}
					}
				});
				$(mainPanel).before(allOption);
			}
			
			function resultbarOverflow($this) {
				$(mainPanel).optionsPanel('disabledNotChecked', true);
			}

			function removeHandler(event, id) {
				$(mainPanel).optionsPanel('checked', id, false);
			}
			
			function resultbarNotOverflow($this) {
				if ($('li', $this.target).attr('id') != options.rootId) {
					$(mainPanel).optionsPanel('disabledAll', false);
				}
			}
			
		}, 
		
		_all : function(dialog) {
			var self = this, options = self.options, optionPanel
			, mainPanel = $('<div class="Clear Pop-con mb10"></div>'), rightPanel = $('<div class="fl Pop-box"></div>')
			, leftPanel = $('<div class="fl Pop-box"><ul></ul></div>');
			
			leftPanel = $(leftPanel).append($('ul', leftPanel).listnav({selecting : navClick, url : ctx + '/ajax/industrys?id=0'}));
			
			mainPanel = $(mainPanel).append(leftPanel).append(rightPanel);
			self.resultbar = $('<div></div>').resultbar({overflow : resultbarOverflow, notOverflow : resultbarNotOverflow, remove : removeHandler });
			$(dialog).empty().append(self.resultbar).append(mainPanel);

			$($.parseJSON($(self.uiValueField).val())).each(function (i, obj) {  // 初始化已有的值
				$(self.resultbar).resultbar('addItem', obj.id, obj);
			});
			function navClick(event, options) {
				optionPanel = $('<div></div>').optionsPanel({
					url : ctx + '/ajax/industrys?id=' + options.selecting.id, 
					click : $.proxy(self.optionClick, self),
					created : mainPanelCreated,
					type : self.options.type
				});
				$(rightPanel).empty().append(optionPanel);
			}

			function mainPanelCreated($this) {
				$.each($(self.resultbar).resultbar('getAllData'), function (i, obj) {  // 把初始值设置为选择
					$(optionPanel).optionsPanel('checked', obj.id, true);
				});
				$(self.resultbar).resultbar('checkSize');  // 检查结果集的大小
			}
			
			function resultbarOverflow($this) {
				$(optionPanel).optionsPanel('disabledNotChecked', true);
			}

			function removeHandler(event, id) {
				$(optionPanel).optionsPanel('checked', id, false);
			}
			
			function resultbarNotOverflow($this) {
				$(optionPanel).optionsPanel('disabledAll', false);
			}
			
		},
		
		optionClick : function(evlent, options) {
			var self = this;
			if (self.options.type == 'single') {
				$(self.resultbar).resultbar('removeAll');
				$(self.resultbar).resultbar('addItem', options.data.id, options.data);
			} else {
				if (options.checked) {
					$(self.resultbar).resultbar('addItem', options.data.id, options.data);
				} else {
					$(self.resultbar).resultbar('removeItem', options.data.id);
				}
			}
		}
	});
	
	/**
	 * 职位类别
	 */
	$.widget("ui.selectCompetency", $.ui.selectWindow, {
		options : {
			width : 640,
			height : 500,
			type : 'multi', // single, multi
			level : 'one' // all, last
		},
		
		_setupContent : function(dialog) {
			var self = this, options = self.options;
			if (options.level == 'one') {
				self._oneAndLast(dialog, ctx + '/ajax/competencys?id=0');
			} else if (options.level == 'last') {
				self._oneAndLast(dialog, ctx + '/ajax/competencys!getTheJsonLastNodeCompetencys');
			} else {
				self._all(dialog);
			}
			
		},
		
		_oneAndLast : function(dialog, url) {
			var self = this, options = self.options
			, mainPanel = $('<div class="pop-selectCompetency Clear"></div>').optionsPanel({
				url : url, 
				click :  $.proxy(self.optionClick, self),
				created : mainPanelCreated,
				type : options.type
			});
			self.resultbar = $('<div></div>').resultbar({overflow : resultbarOverflow, notOverflow : resultbarNotOverflow, remove : removeHandler });
			$(dialog).empty().append(self.resultbar).append(mainPanel);

			function mainPanelCreated($this) {
				$($.parseJSON($(self.uiValueField).val())).each(function (i, obj) {  // 初始化已有的值
					$(mainPanel).optionsPanel('checked', obj.id, true);
					$(self.resultbar).resultbar('addItem', obj.id, obj);
				});
			}
			
			function resultbarOverflow($this) {
				$(mainPanel).optionsPanel('disabledNotChecked', true);
			}

			function removeHandler(event, id) {
				$(mainPanel).optionsPanel('checked', id, false);
			}
			
			function resultbarNotOverflow($this) {
				$(mainPanel).optionsPanel('disabledAll', false);
			}
		},
		
		_all : function(dialog) {
			var self = this, optionPanel
			, mainPanel = $('<div class="Clear Pop-con mb10"></div>'), rightPanel = $('<div class="fl Pop-box"></div>')
			, leftPanel = $('<div class="fl Pop-box"><ul></ul></div>');
			leftPanel = $(leftPanel).append($('ul', leftPanel).listnav({selecting : navClick, url : ctx + '/ajax/competencys?id=0'}));
			mainPanel = $(mainPanel).append(leftPanel).append(rightPanel);
			self.resultbar = $('<div></div>').resultbar({overflow : resultbarOverflow, notOverflow : resultbarNotOverflow, remove : removeHandler });
			$(dialog).empty().append(self.resultbar).append(mainPanel);
			
			$($.parseJSON($(self.uiValueField).val())).each(function (i, obj) {  // 初始化已有的值
				$(self.resultbar).resultbar('addItem', obj.id, obj);
			});
			function navClick(event, options) {
				optionPanel = $('<div></div>').optionsPanel({
					url : ctx + '/ajax/competencys?id=' + options.selecting.id, 
					click : $.proxy(self.optionClick, self),
					created : mainPanelCreated,
					type : self.options.type
				});
				$(rightPanel).empty().append(optionPanel);
			}
			
			function mainPanelCreated($this) {
				$.each($(self.resultbar).resultbar('getAllData'), function (i, obj) {  // 把初始值设置为选择
					$(optionPanel).optionsPanel('checked', obj.id, true);
				});
				$(self.resultbar).resultbar('checkSize');  // 检查结果集的大小
			}
			
			function resultbarOverflow($this) {
				$(optionPanel).optionsPanel('disabledNotChecked', true);
			}

			function removeHandler(event, id) {
				$(optionPanel).optionsPanel('checked', id, false);
			}
			
			function resultbarNotOverflow($this) {
				$(optionPanel).optionsPanel('disabledAll', false);
			}
			
		},
		
		optionClick : function(event, options) {
			var self = this;
			if (self.options.type == 'single') {
				$(self.resultbar).resultbar('removeAll');
				$(self.resultbar).resultbar('addItem', options.data.id, options.data);
			} else {
				if (options.checked) {
					$(self.resultbar).resultbar('addItem', options.data.id, options.data);
				} else {
					$(self.resultbar).resultbar('removeItem', options.data.id);
				}
			}
		}
	});

}(jQuery));
