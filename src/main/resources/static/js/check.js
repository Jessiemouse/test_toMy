


    $.ajax({
        type: 'GET',
        url: "/user/session",
        contentType: 'application/json',
        success:function(responseData) {

        },
        error:function (){
            alert("請先進行登入");
            window.location.href ="index";
            return; // 錯誤發生時立即結束函式，不執行其他的JavaScript代碼
        }
    })