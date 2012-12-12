
(function( $, undefined ) {
	
	/**
	 * 从业时间
	 */
	$.widget("ui.eSelectBox", {
		options: {
			data : [],
			width : 0,
			blankText : '--请选择--',
			blankValue : ''
		},
		
		_create : function() {
			var self = this, options = self.options, tmpl = '<option value="${value}">${text}</option>'
				, defaultValue = $(self.element).val()
				, currentId = $(self.element).attr('id')
				, parentEl = $(self.element).parent()
				, cssClass = $(self.element).attr('class')
				, currentSelect = $($.tmpl('<select id="${id}" name="${id}"></selected>', {id : currentId}));
			$(self.element).after(currentSelect);
			$(self.element).remove();
			self.element = currentSelect;
			$(self.element).append($.tmpl('<option value="${value}">${text}</option>', {value : options.blankValue, text : options.blankText}));
			$(options.data).each(function(index, obj) {
				var option = $($.tmpl(tmpl, {value : obj.id, text : obj.text}));
				if (defaultValue == obj.id) {
					option = $(option).prop('selected', true);
				}
				$(self.element).append(option);
			});
			if (options.width != 0) {
				$(self.element).css('width', options.width);
			}
			$(self.element).attr('class', cssClass);
			// $(self.element).selectBox();
		},
		
		_init : function() {
			var self = this;
		}
	});
	
	$.extend($.ui.eSelectBox, {
		version: "1.8.20",
		
	});

	/**
	 * 关键词
	 */
	$.widget("ui.eKeyBox", {
		options: {
			width : 0,
			length : 6,
			required : false,
			form : ''
		},
		
		elements : [],
		
		_create : function() {
			var self = this, options = self.options, currentId = $(self.element).attr('id')
			, currentValue = $(self.element).val(), values = currentValue.split(',')
			, valueField = (self.valueField = $($.tmpl('<input type="hidden" id="${id}" name="${id}" />', {id : currentId})));
			$(valueField).val(currentValue);
			$(self.element).attr('id', 'key_' + currentId).attr('name', 'key_' + currentId);
			$(self.element).val('');
			self.errorMessage = $('<div class="validateError">这是必须的。</div>');

			$(self.element).keyup(function (event) {
				var val = '';
				$(self.elements).each(function (i, o) {
					if (i > 0 && $(o).val()) {
						val += ',';
					}
					val += $(o).val();
				});
				$(valueField).val(val);
				
				if (val != '') {
					$(self.errorMessage).css('display', 'none');
				} else {
					$(self.errorMessage).css('display', 'inline');
				}
			});
			
			for (i = 0; i < options.length - 1; i++) {
				var newEl = $(self.element).clone(true), val = '';
				if (values.length > i) {
					val = values[i];
				}
				newEl = $(newEl).val(val);
				self.elements.push(newEl);
				$(self.element).before(self.elements[self.elements.length - 1]);
			}
			
			if (values.length >= options.length) {
				$(self.element).val(values[options.length - 1]);
			}
			self.elements.push(self.element);
			$(self.element).after(valueField);
			
			if (options.required) {
				$('#' + options.form).submit(function(event) {
					if ($(self.valueField).val() == '') {
						$(self.valueField).after(self.errorMessage);
						$(self.errorMessage).css('display', 'inline');
						return false;
					}
				});
			}
		}
	});
	
	$.extend($.ui.eKeyBox, {
		version: "1.8.20",
		
	});

	/**
	 * 多选框
	 */
	$.widget("ui.eCheckbox", {
		options: {
			value : null,
			data : [],
			required : false,
			form : ''
		},
		
		elements : [],
		
		_create : function() {
			var self = this, options = self.options, elId = $(self.element).attr('id')
			, valueField = (self.valueField = $($.tmpl('<input type="hidden" id="${id}" name="${id}" value="">', {id : elId})))
			, checkboxtmpl = '<label for="${id}"><input type="checkbox" id="checkbox_${id}" name="checkbox_${id}" value="${value}" />${label}</label>'
			, value = $(self.element).html();
			self.errorMessage = $('<div class="validateError">这是必须的。</div>');
			$(valueField).val(value);
			
			if ($.isArray(options.items)) {
				$(self.element).empty();
				$(options.items).each(function(index, obj) {
					var checkbox = $($.tmpl(checkboxtmpl, {id : elId, value : obj.value, label : obj.label}));
					
					self.elements.push(checkbox);
					$(self.element).append(checkbox);
					$(checkbox).change(self, onChange);
					
					if (self.isChecked(value, obj.value)) {
						$('input', checkbox).prop('checked', true);
					}
				});
				$(self.element).append(valueField);
			}
			

			if (options.required) {
				$('#' + options.form).submit(function(event) {
					if ($(self.valueField).val() == '' || $(self.valueField).val() == 0) {
						$(self.valueField).after(self.errorMessage);
						$(self.errorMessage).css('display', 'inline');
						return false;
					}
				});
			}
			
			/**
			 * 复选框值改变的时候
			 */
			function onChange(event) {
				var v = 0;
				$(':checked', self.element).each(function (index, obj) {
					v += parseInt($(obj).val());
				});
				
				if (v == 0) {
					$(self.errorMessage).css('display', 'inline');
				} else {
					$(self.errorMessage).css('display', 'none');
				}
				$(valueField).val(v);
			}
		},
		
		/**
		 * 求对数
		 */
		log : function(value, base) {
			return Math.log(value) / Math.log(base);
		},
		
		/**
		 * 判断是否选择checkbox
		 */
		isChecked : function(val, optionValue) {
			var self = this;
			if (val && val > 0) {
				if (val % 2 != 0 && optionValue == 1) { // 如果是奇数，并且optionValue==1，那么直接选择。
					return true;
				}
				var n = 0; // 用于计算2的整次幂相加的结果
				var l = val; // 存放val-n的值，每超找到一个值就递减依次
				while (l > 1) {
					var cm = (Math.pow(2, self.log(l, 2) >> 0)) >> 0;  // 找出里val最近的，并且小于val的2的整次幂
					n += cm;
					l = val - n;
					if (cm == optionValue) {
						return true;
					}
				}
			}
			return false;
		}
	});
	
	$.extend($.ui.eCheckbox, {
		version: "1.8.20",
		
	});
	
	/**服务过的企业**/
	$.widget("ui.serviceCompany", {
		options: {
			length : 3,
			tmpl : '<ul><li><label for="enter_hy_${id}">所在行业：<input type="text" id="enter_hy_${id}" name="enter_hy_${id}" class="input w150"/></label></li>' +
			'<li><label for="enter_name_${id}">公司名称：<input type="text" id="enter_name_${id}" name="enter_name_${id}" class="input w150"/></label></li></ul>',
			buttonText : '继续增加'
		},

		
		elements : [],
		
		_create : function() {
			var self = this, options = self.options
			, valueField = (self.valueField = $('input', self.element))
			, addBtn = (self.addBtn = $($.tmpl('<div><a style="padding: 0" href="#">${text}</a></div>', {text : options.buttonText})));
			$(addBtn).addClass('f12').click(addBtnHandler);
			
			//新建3个input且添加keyup事件
			for (i = 0; i < options.length; i++) {
				var input = $($.tmpl(options.tmpl, {id : i}));//新建input
				$(input).keyup(keyupHandler);//给新建的input添加事件
				self.elements.push(input);//把新建的input添加到elements里面，elements长度加1
				$(self.element).append(self.elements[self.elements.length - 1]);//新建的input追加到self.element里面
			}
			$(self.element).append(addBtn);
			
			//keyup函数,将input值放到隐藏域
			function keyupHandler(event) {
				var val = '';
				$(self.elements).each(function(index, obj) {
					var hyVal = $('input[id=enter_hy_'+ index + ']', obj).val();
					var nameVal = $('input[id=enter_name_'+ index + ']', obj).val();
					if (hyVal && nameVal) {						
						if (index > 0) {
							val += ';';
						}
						val +=  hyVal + ',' + nameVal;
					}
				});
				$(valueField).val(val);
			}
			
			function addBtnHandler(event) {
				self.insertRow();
				event.preventDefault();
			}
			
		},
		
		insertRow : function () {
			var self = this, options = self.options
			, input = $($.tmpl(options.tmpl, {id : self.elements.length - 1}));//新建input
			self.elements.push(input);//把新建的input添加到elements里面，elements长度加1
			$(self.addBtn).before(input);
		},
		
		_init : function() {
			var self = this, valueField = $(self.valueField).val();
			
			if (valueField) {
				var array = valueField.split(';');
				$(array).each(function(index, obj) {
					var objVals = obj.split(',');
					$('input[id=enter_hy_'+ index + ']', self.element).val(objVals[0]);
					$('input[id=enter_name_'+ index + ']', self.element).val(objVals[1]);
				});
			}
		}
	});
	
	
	/**
	 * 公司座机
	 */
	$.widget("ui.tel", {
		options: {
			width : {
				qh : 50,
				zj : 80,
				fj : 50
			},
			tmpl : '<input type="text" id="qh_${id}" name="qh_${id}" class="input-text" style="width:${qh}px;">- <input type="text" id="zj_${id}" name="zj_${id}" class="input-text" style="width:${zj}px;" >- <input type="text" id="fj_${id}" name="fj_${id}" class="input-text" style="width:${fj}px;" >'
		},
		
		elements : [],
		
		_create : function() {
			var self = this, options = self.options, currentId = $(self.element).attr('id')
			, currentValue = $(self.element).val(), values = currentValue.split('-')
			, inputPanel = $($.tmpl(options.tmpl, {id : currentId, qh : options.width.qh, zj : options.width.zj, fj : options.width.fj}));
			
			
			$(self.element).before(inputPanel);
			$(self.element).hide();
			
			//如果隐藏域里面有值，则输出到页面
			if (values && values.length > 1) {
				$('input[id=qh_' + currentId.replace(/\./g, '\\.') + ']').val(values[0]); // 区号
				$('input[id=zj_' + currentId.replace(/\./g, '\\.')  + ']').val(values[1]);  // 电话号码
				if (values.length > 2) {
					$('input[id=fj_' + currentId.replace(/\./g, '\\.')  + ']').val(values[2]);  // 分机号码
				}
			}

			//添加keyup事件，将value值写入到隐藏域
			$(inputPanel).keyup(function (event) {
				
				var val = '';
				var qhVal = $('input[id=qh_' + currentId.replace(/\./g, '\\.') + ']').val(); // 区号
				var zjVal = $('input[id=zj_' + currentId.replace(/\./g, '\\.')  + ']').val();  // 电话号码
				var fjVal = $('input[id=fj_' + currentId.replace(/\./g, '\\.')  + ']').val();  // 分机号码

				if (qhVal != '' && zjVal != '') {
					val = qhVal + '-' + zjVal;
					if (fjVal != '') {
						val += '-' + fjVal;
					}
				}
				$(self.element).val(val);
				
			});
			
		}
	});
	
	$.extend($.ui.companyTel, {
		version: "1.8.20",
		
	});
	
	/**年薪范围**/
	$.widget("ui.Salary", {
		options: {
			tmpl : '<a href="javascript:;" data-val="">全部</a><a href="javascript:;" data-val="10-15" >10-15万</a><a href="javascript:;" data-val="15-20">15-20万</a><a href="javascript:;" data-val="20-30">20-30万</a><a href="javascript:;" data-val="30-50">30-50万</a><a href="javascript:;" data-val="50-100">50-100万</a>'
		},
		elements : [],
		_create : function() {
			var self = this, options = self.options,inputPanel = $($.tmpl(options.tmpl));
			$(self.element).prepend(inputPanel);//写入到页面

			//添加click事件并将a标签的值传到input中
			$(inputPanel).click(function(event){
				var thas = $(this);
				if(thas.hasClass('current')){return;}
				inputPanel.removeClass('current');
				thas.addClass('current');
				var val = thas.attr('data-val').split("-");
				$('input[id=salarylow]').val(val[0]);
				$('input[id=salaryhigh]').val(val[1]);
			});
		}
	});
	
	/*** 把数字表示的内容用字符串输出*/
	$.widget("ui.display", {
		options : {
			data : []
		},
		
		_create : function() {
			var self = this, options = self.options, value = $(self.element).html();
			$(options.data).each(function(index, object) {
				if (object.id == value) {
					$(self.element).empty().append(object.text);
					return;
				}
			});
		}
	});

	/**
	 * 统计到目前为止一共多少个年头
	 */
	$.widget("ui.countYear", {
		options : {
			tmpl : '${number}年从业经验。'
		},
		
		_create : function() {
			var self = this, options = self.options, value = $(self.element).html();
			var currentYear = new Date().getFullYear();
			$(self.element).empty().append($.tmpl(options.tmpl, {number : (currentYear - parseInt(value))}));
		}
	});

	
	//手机号码验证       
	jQuery.validator.addMethod("isMobile", function(value, element) {       
		var length = value.length;
		var mobile = /^0?1[358]\d{9}$/;
		return this.optional(element) || (length == 11 && mobile.test(value));       
	}, "请正确填写您的手机号码");  	
	//电话号码验证       
	jQuery.validator.addMethod("isTel", function(value, element) {       
		var tel = /^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
		return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的公司座机"); 
	// 身份证号码验证 
	jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
	  return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
	}, "请正确输入您的身份证号码"); 
	// 联系电话(手机/电话皆可)验证 
	jQuery.validator.addMethod("isPhone", function(value,element) { 
	  var length = value.length;
	  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
	  var tel = /^\d{3,4}-?\d{7,9}$/;
	  return this.optional(element) || (tel.test(value) || mobile.test(value)); 
	}, "请正确填写您的联系电话"); 


	
	
}(jQuery));
