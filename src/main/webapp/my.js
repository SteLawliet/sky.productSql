
jQuery(function () {
    var proBody = jQuery('.proBody');
    var data ;
    var path = '/Users/zhaoziqi/Documents/Study/JAVA/IntelliJProject/img/';

    var page = {
        currentPage:0,
        size : 12,
        total:1,
        maxPage: function(){
            return this.total/this.size;
        },
        start:function(){ return this.currentPage*this.size},
        end: function(){ return this.start()+this.size-1}
    };

    var max = 0;
    jQuery.getJSON("/pro/do?fun=select",function (data0) {

        data = data0;
        page.total = data.length;
        max = data0.length/page.size;
        showPro(0,11,data);

    });




    jQuery('.nextPage').click(function () {

        page.currentPage=page.currentPage+1;
        if (page.currentPage  > max){
            page.currentPage = page.currentPage-1;
            return;
        }
        var star = page.currentPage*page.size;
        var end = star+page.size-1;
        showPro(star,end,data);

    });

    jQuery('.prevPage').click(function () {
        page.currentPage=page.currentPage-1;
        if (page.currentPage  < 0){
            page.currentPage = 0;
            return;
        }
        var star = page.currentPage*page.size;
        var end = star+page.size-1;
        showPro(star,end,data);
    });


    function showPro(star,end) {

        jQuery('.pro').remove();
        if (star!==0){
            var i0 = 0;
            console.log(data.length);
            for (var j=star;j<=end;j++){
                if (!(data[j].unitPrice)){
                    return;
                }
                pro1 = jQuery(".pro0").clone();
                pro1.removeClass('pro0');
                pro1.addClass('pro');
                pro1.find('.price').text(data[j].unitPrice+"￥");
                pro1.find('img').attr("src",path+data[j].path);
                pro1.find('h6').text(data[j].name);
                pro1.find('.quantity').text(data[j].quantity);
                pro1.find('.no').text(data[j].no);
                proBody = jQuery('.proBody');
                pro1.appendTo(proBody);
            }


        }else {
            for (var i = star; i <= end; i++) {
                pro1 = jQuery(".pro0").clone();
                pro1.removeClass('pro0');
                pro1.addClass('pro');
                pro1.find('.price').text(data[i].unitPrice + "￥");
                pro1.find('img').attr("src",path + data[i].path);
                pro1.find('h6').text(data[i].name);
                pro1.find('.quantity').text(data[i].quantity);
                pro1.find('.no').text(data[i].no);
                pro1.appendTo(proBody);
            }

        }
    }


    proBody.on('click','.add',function () {
        var thumbnail= jQuery(this).parent().parent('.product');
        var Val=thumbnail.find('.quantity');
        var changeVal =Val.html();
        changeVal= parseInt(changeVal)+1;

        var url = '/pro/do?fun=updateQuantity&changeval='+changeVal;
        url = url+'&whereno='+thumbnail.find('.no').text();
        jQuery.getJSON(url,function (date) {
            Val.text(date);
        })
    });

    proBody.on('click','.sub',function () {
        var thumbnail= jQuery(this).parent().parent('.product');
        var Val=thumbnail.find('.quantity');
        var changeVal =Val.text();
        changeVal= parseInt(changeVal)-1;
        if (changeVal<0){
            return;
        }
        var url = '/pro/do?fun=updateQuantity&changeval='+changeVal;
        url = url+'&whereno='+thumbnail.find('.no').text();
        jQuery.getJSON(url,function (date) {

            Val.text(date);

        })
    });

    jQuery(window).scroll(function () {
        if (jQuery(window).scrollTop()>119){
            jQuery('nav').addClass('navbar-fixed-top');


        }else {

            jQuery('nav').removeClass('navbar-fixed-top');
        }
    });

});

