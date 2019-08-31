var animation = window.animation || {};

animation = {
    elements: '<div class="loading animation-loading">' +
                '<span></span>' +
                '<span></span>' +
                '<span></span>' +
                '<span></span>' +
                '<span></span>' +
            '</div>',

    load: function(){
        $(animation.elements).appendTo('body');
    },

    close: function(){
        $('.animation-loading').remove();
    }
}




