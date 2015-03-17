function updateFragment(thiz) {
    var $this = jQuery(thiz);
    jQuery.ajax({
        url: $this.attr("href"),
        type: 'GET',
        success: function (response) {
            jQuery($this.data("update")).replaceWith(response);
        }
    });
    return false;
}