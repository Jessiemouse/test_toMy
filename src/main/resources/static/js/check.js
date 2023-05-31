

// $(document).ready(function(){
Checklogin()
// }
function Checklogin() {
    var ulElement = document.querySelector('.list-unstyled');
    ulElement.innerHTML = '';
    $.ajax({
        type: 'GET',
        url: "/session",
        contentType: 'application/json',
        success: function (responseData){}

    });
}