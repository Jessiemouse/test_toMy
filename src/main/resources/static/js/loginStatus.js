$(document).ready(function() {
    $('.dropdown-toggle').click(function(e) {
        e.preventDefault();
        $(this).parent().toggleClass('open');
    });
});