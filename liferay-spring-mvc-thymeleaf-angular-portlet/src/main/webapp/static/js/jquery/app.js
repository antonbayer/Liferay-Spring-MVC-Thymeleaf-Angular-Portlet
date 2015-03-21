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

function updateFragments(thiz) {
    var $this = jQuery(thiz);
    jQuery.ajax({
        url: $this.attr("href"),
        type: 'GET',
        success: function (response) {
            var entries = parseFragments($this.data("update"));
            for (var prop in entries) {
                jQuery(entries[prop]).replaceWith(response[prop]);
            }
        }
    });
    return false;

    function parseFragments(text) {
        var blocks = text.split("|");
        var params = {};
        for (var i = 0; i < blocks.length; i++) {
            var entry = blocks[i].split("=");
            params[entry[0]] = entry[1];
        }
        return params;
    }
}