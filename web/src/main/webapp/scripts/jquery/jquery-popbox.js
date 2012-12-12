(function() {

    $.fn.popbox = function(options) {
        var _self = this;
        var settings = $.extend({
            selector : this.selector,
            open : '.popup_open',
            box : '.popup_box',
            boxwidth : '200px',
            arrow : '.popup_arrow',
            arrow_border : '.popup_arrow-border',
            close : '.popup_close',
            beforeopen : $.noop
        }, options);

        var methods = {
            open : function(event) {
                event.preventDefault();
                // if (event && event.stopPropagation)
                    // event.stopPropagation()
                // else
                    // window.event.cancelBubble = true
                var pop = $(this);
                var box = $(this).parent().find(settings['box']);
                box.width(settings.boxwidth);

                box.find(settings['arrow']).css({
                    'left' : box.width() / 2 - 10
                });
                box.find(settings['arrow_border']).css({
                    'left' : box.width() / 2 - 10
                });

                if (box.css('display') == 'block') {
                    methods.close();
                } else {
                    box.css({
                        'display' : 'block',
                        'top' : 10,
                        'left' : ((pop.parent().width() / 2) - box.width() / 2 )
                    });
                }
                if ($.isFunction(settings['beforeopen'])) {
                    settings['beforeopen'](box);
                }
                $(document).bind('keyup', keyCodeClose);
                $(document).bind('click', documentClose);
            },

            close : function() {
                var box = _self.find(settings['box']);
                box.fadeOut("fast");
                $(document).unbind('keyup', keyCodeClose);
                $(document).unbind('click', documentClose);
            }
        };

                
        function keyCodeClose(event){
            if (event.keyCode == 27) {
                methods.close();
            }
        }
        function documentClose(event){
            if (!$(event.target).closest(_self).length) {
                methods.close();
            }
        }
        

        return this.each(function() {
            $(settings['open'], this).bind('click', methods.open);
        });
    }
}).call(this);
