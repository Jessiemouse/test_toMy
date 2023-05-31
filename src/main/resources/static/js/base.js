function sidebar_open() {
  // document.getElementById("mySidebar").style.display = "block";
  $("#mySidebar").animate(
    {
      left: "0px",
    },
    150,
    "linear"
  );
}

function sidebar_close() {
  // document.getElementById("mySidebar").style.display = "none";
  $("#mySidebar").animate(
    {
      left: "-200px",
    },
    150,
    "linear"
  );
}
$(function () {
  $("#mySidebar").click(function (e) {
    e.stopPropagation();
  });
  $("#phoneMenu").click(function (e) {
    e.stopPropagation();
    sidebar_open();
  });
  $("html").click(sidebar_close);
});
