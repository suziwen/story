// 创建一个闭包    
(function($) {
	// 插件的定义
	$.fn.eCheckbox = function(options) {
		// build main options before element iteration
		var opts = $.extend({}, $.fn.eCheckbox.defaults, options);
		// iterate and reformat each matched element
		return this.each(function() {
			$this = $(this);
			// build element specific options
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			
			// call our build function
			var markup = $.fn.eCheckbox.build($this, o);
			var id = $this.attr('id');
			$this.attr('id', 'div_' + id);
			$this.html(markup);
			
			$('input[type=checkbox]', $this).change($this, onChange);

			/**
			 * 复选框值改变的时候
			 */
			function onChange(e) {
				var v = 0;
				$(':checked', e.data.context).each(function (index, element) {
					v += parseInt($(element).val());
				});
				$('input[type=hidden]', e.data.context).val(v);
			}
		});
	};
	
	$.fn.eCheckboxHtml = function (options){
		var opts = $.extend({}, $.fn.eCheckbox.defaults, options);
		return this.each(function() {
			$this = $(this);
			// build element specific options
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			
			// call our build function
			var markups = $.eCheckboxbuildItems(me.html,opts.items);
			var id = $this.attr('id');
			$this.attr('id', 'div_' + id);
			$this.html(markups.join(","));
		});
	}
	
	/**
	 * 求对数
	 */
	function log(value, base) {
		return Math.log(value) / Math.log(base);
	}
	
	$.eCheckboxbuildItems = function(value,items){
		var returnItems = [];
		$.each(items,function(index,item){
			if(isselected(value,item.value)){
				returnItems.push(item.label);
			}
		});
		return returnItems;
	}
	// 定义暴露build函数
	$.fn.eCheckbox.build = function(me, opts) {
		var id = 'check_' + me.attr('id'), html = '<input type="hidden" id="' + me.attr('id');
		html += '" name="' + me.attr('id');
		html += '" value="' + $.trim(me.html()) + '">';
		if ($.isArray(opts.items)) {
			$(opts.items).each(function(index, element) {
				html += '<label for="check_' + me.attr('id') + '">';
				html += '<input type="checkbox" name="check_' + me.attr('id') + '" id="check_' + me.attr('id') + '" value="' + element.value + '"';
				if (isselected(me.html(), element.value)) {
					html += ' checked="selected" ';
				}
				html += '/>';
				html += element.label +'</label>';
			});
		}
		return html;
	};
	
	/**
	 * 选中checkbox
	 */
	function isselected(val, itemValue) {
		if (val && val > 0) {
			if (val % 2 != 0 && itemValue == 1) { // 如果是奇数，并且itemValue==1，那么直接选择。
				return true;
			}
			var n = 0; // 用于计算2的整次幂相加的结果
			var l = val; // 存放val-n的值，每超找到一个值就递减依次
			while (l > 1) {
				var cm = (Math.pow(2, log(l, 2) >> 0)) >> 0;  // 找出里val最近的，并且小于val的2的整次幂
				n += cm;
				l = val - n;
				if (cm == itemValue) {
					return true;
				}
			}
		}
		return false;
	};
	
	// 插件的defaults
	$.fn.eCheckbox.defaults = {
		items : [],
		value : null
	};
	
	// 闭包结束
})(jQuery);
