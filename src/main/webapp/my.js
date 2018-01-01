
jQuery(function () {
    var proBody = jQuery('.proBody');
    var data ;
    var path = 'img/';

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

    jQuery('.search').click(function () {
        var url = "/pro/do?fun=";

        url=url+'select'

        jQuery.getJSON(url,function (data0) {

            data = data0;
            page.total = data.length;
            max = data0.length / page.size;
            showPro(0, 11, data);
        });
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
               show(j);
            }


        }else {
            for (var i = star; i <= end; i++) {
                show(i);
            }

        }
    }

    function show(i1) {
       var pro1 = jQuery(".proBody .pro0").clone();
        pro1.removeClass('pro0');
        pro1.addClass('pro');
        pro1.find('.price').text(data[i1].unitPrice + "￥");
        pro1.find('img').attr("src",path + data[i1].path);
        pro1.find('h6').text(data[i1].name);
        pro1.find('.pro_index').text(i1);
        pro1.find('.quantity').text(data[i1].quantity);
        pro1.find('.no').text(data[i1].no);
        pro1.find('.temp').text(data[i1].temp);
        console.log(data[i1].temp);;
        pro1.appendTo(proBody);
    }

    proBody.on('click','.thumbnail',function () {
        var thumbnail= jQuery(this);
        console.log(this);
        var index = thumbnail.find('.pro_index').text();
        index = parseInt(index);
        var tempVal = parseInt(data[index].temp);
        tempVal=tempVal+1;
        data[index].temp=tempVal;
        jQuery('.t_pro').remove();
        for (var i = 0;i<data.length;i++){
            if ( data[i].temp !== 0){
                var pro = jQuery(".tr_pro.pro0").clone();
                pro.addClass('t_pro');
                pro.removeClass('pro0');
                pro.find('.t_no').html(data[i].no);
                pro.find('.t_name').html(data[i].name);
                pro.find('.t_quantity').html(data[i].quantity);
                pro.find('.t_temp').html(data[i].temp);
                pro.appendTo('tbody');
            }
        }

        // var url = '/pro/do?fun=updateQuantity&changeval='+changeVal;
        // url = url+'&whereno='+thumbnail.find('.no').text();
        // jQuery.getJSON(url,function (date) {
        //     Val.text(date);
        // })
    });

    proBody.on('click','.add',function () {
        var thumbnail= jQuery(this).parent().parent('.product');
        var Val=thumbnail.find('.quantity');
        var changeVal =Val.text();
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

