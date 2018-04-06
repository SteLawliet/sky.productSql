
jQuery(function () {
    //显示product的div
    var proBody = jQuery('.proBody');
    var search = jQuery('.search');
    //后台获取的json数据
    var data ;
    //img路径
    var path = 'img/';
    //最大页数
    var max = 0;
    var username;

    var page = {
        currentPage:0,
        size : 12,
        total: 1
    };
    /**
     *   findAll()
     *   刷新显示所有product
     */


    jQuery('#login').click(function () {

        var json = jQuery('#f_login').serializeArray();

        json = JSON.stringify(json);
        jQuery.post("/pro/do?fun=login", {
            'json': json
        }, function (data0) {
            data0 = JSON.parse(data0);
            if (data0 == null || parseInt(data0.no) === -1) {
                alert('密码或用户名错误');
            } else {
                jQuery.cookie('username', data0.name);
                jQuery.cookie('no', data0.no);
                jQuery.cookie('pwd', data0.pwd);
                window.location.assign('index.html');
                jQuery('#lin').addClass('no_display');
                jQuery("#in").addClass('no_display');
            }
        });
    });
    var flag0 = true;
    var flag1 = true;


    jQuery('#user0').focusout(function () {
        var username = jQuery(this).val();
        if (username.trim() === "") {
            alert("用户名不能为空");
            return;
        }
        jQuery.getJSON(url, {
            'fun': 'username',
            'username': username
        }, function (data0) {
            if (!parseInt(data0)) {
                alert("用户名已经存在");
                flag0 = false;
            } else {
                alert("用户名未注册");
                flag0 = true;
            }
        })
    });

    var url = "/pro/do";

    jQuery('#psw2').focusout(function () {
        var psw1 = jQuery('#psw1').val();
        var psw2 = jQuery('#psw2').val();
        if (psw1 !== psw2) {
            alert("俩次密码输入不一致");
            flag1 = false;
        } else {
            flag1 = true;
        }
    });

    jQuery('#signup').click(function () {
        if (!flag0) {
            alert("用户名错误");
            return;
        }
        if (!flag1) {
            alert("密码不一致");
            return;
        }
        var username = jQuery("#user0").val().trim().toLowerCase();
        jQuery.post(url, {
            'fun': 'signup',
            'username': username,
            'pwd': jQuery('#psw1').val()
        }, function (data0) {
            data0 = JSON.parse(data0);
            if (data0 == null || parseInt(data0.no) === -1) {
                alert('密码或用户名错误');
            } else {
                jQuery.cookie('username', data0.name);
                jQuery.cookie('no', data0.no);
                jQuery.cookie('pwd', data0.pwd);
                window.location.assign('index.html');
                jQuery('#lin').addClass('no_display');
                jQuery('#in').addClass('no_display');
            }
        });
    });

    jQuery('#out').on('click', function () {
        jQuery.removeCookie('username');
        jQuery.removeCookie('no');
        window.location.assign('index.html');
        jQuery('#lin').removeClass('no_display');
        jQuery('#in').removeClass('no_display');
        jQuery('#out').addClass('no_display');

    });
    reload();

    function reload() {
        jQuery.getJSON("/pro/do?fun=select", function (data0) {
            username = jQuery.cookie('username');
            if (username == null || username == 0) {
                return;
            }
            data = data0;
            page.total = data.length;
            max = data0.length / page.size;
            jQuery('#user_name').text(username + ' ');
            showPro(0, 11, data);
        });
    }


    /**
     *  .search的click监听
     *   搜索功能.分类搜索和模糊搜索
     */
    search.click(function () {
        var url = "/pro/do";
        var searchVal = search.prev('input[type=text]').val();
        page.currentPage = 0;
        jQuery.getJSON(url, {
            'fun': 'select',
            'search': searchVal
        }, function (data0) {
            data = data0;
            page.total = data.length;
            max = data0.length / page.size;
            showPro(0, 11, data);
        });
    });

    var t_modal = jQuery('#modal_table');
    var login_modal = jQuery('#modal-login');
    jQuery('.t_confirm').click(function () {
        var t_form = jQuery('.t_form').clone().addClass('t_form0').remove('t_form');
        var t_submit = jQuery('.submit_table').append(t_form);
        t_modal.modal({backdrop: 'static'}, 'show');

    });

    t_modal.on('hide.bs.modal', function () {
        // $('#myModal').modal('show');
        jQuery('.t_form0').remove();
    });

    jQuery('.t_submit').click(function () {
        var url = "/pro/do";
        var json = jQuery('.column .t_form').serializeArray();
        var info = '';
        if (json.length === 1) {
            alert('未选中任何产品');
            return;
        }
        json.splice(0, 1);
        if (change_.attr('change') === 'sub') {
            info = '确认提交出货订单?';
            for (var it = 0; it < json.length; it++) {
                json[it].value = 0 - json[it].value;
            }
        } else {

            info = '确认提交入库订单?';
        }
        json = JSON.stringify(json);
        var supplier = jQuery('#select0').val();
        //todo
        console.log(json);

        jQuery.post(url, {
            'fun': 'tempChange',
            'json': json,
            'no': jQuery.cookie('no'),
            'supplier': supplier
        }, function () {
            alert('success ' + jQuery.cookie('no'));
            window.location.assign('index.html');
        });

    });

    jQuery('#home').click(function () {
        window.location.assign('index.html');
    });
    var change_ = jQuery('#change');
    jQuery('#pro_in').click(function () {
        reload();
        jQuery(this).parent().addClass('nav_active').siblings().removeClass('nav_active');
        change_.text('入库(+)').attr('change', 'add');
        show_tran.addClass('no_display');
        jQuery('#show_index').removeClass('no_display');


    });
    jQuery('#pro_out').click(function () {
        reload();
        jQuery(this).parent().addClass('nav_active').siblings().removeClass('nav_active');
        change_.text('出库(-)').attr('change', 'sub');
        show_tran.addClass('no_display');
        jQuery('#show_index').removeClass('no_display');

    });

    var show_tran = jQuery('#show_tran');

    var t_data = null;
    jQuery('#pro_update').click(function () {
        jQuery(this).parent().addClass('nav_active').siblings().removeClass('nav_active');
        jQuery('#show_index').addClass('no_display');
        show_tran.removeClass('no_display');
        var url = "/pro/do";
        jQuery.getJSON(
            url,
            {
                'fun': 'gettrans'
            }, function (data0) {
                t_data = data0;
                showTrans(data0);
            }
        )
    });

    var show_tran_body = jQuery('#show_tran_body');

    show_tran_body.on('click', '.tran_tr button', function (e) {
        var index = jQuery(this).parent().parent().attr('index');
        var url = "/pro/do";
        var json = t_data[index].productList;
        json = JSON.stringify(json);
        console.log(json);
        jQuery.post(
            url, {
                'fun': 'confirmChange',
                'json': json,
                'purchase_no': t_data[index].tran_no
            }, function (data0) {
                alert("success");
                jQuery('#pro_update').trigger('click');
            }
        );
        e.stopPropagation();//阻止事件冒泡
    });


    show_tran_body.on('mouseenter', '.tran_tr', function () {
        var index = jQuery(this).attr('index');
        show_tran_pro(t_data[index].productList);
        console.log(t_data[index].productList);
    });

    function showTrans(data) {
        jQuery('.tran_tr').remove();
        for (var i = 0; i < data.length; i++) {
            var clone = jQuery(".tran_clone").clone();
            var tran_tr = clone
                .removeClass('tran_clone').addClass('tran_tr');
            var inout = data[i].unitprice > 0 ? '入库' : '出库';
            var tran = data[i];
            tran_tr.attr('index', i);
            tran_tr.children('.tran_no').text(tran.tran_no);
            tran_tr.children('.tran_date').text(tran.tran_date.trim());
            tran_tr.children('.tran_desc').text(tran.tran_desc);
            tran_tr.children('.tran_inout').text(inout);
            tran_tr.children('.tran_unitprice').text(tran.unitprice);
            tran_tr.children('.tran_supplier').text(tran.units_ordered);
            tran_tr.find('button').attr('index', i).attr("disabled", data[i].confirm);
            tran_tr.appendTo(show_tran_body);
        }
    }

    var show_pro_body = jQuery('#show_pro_body');

    function show_tran_pro(data) {
        jQuery('.pro_tr').remove();
        for (var i = 0; i < data.length; i++) {
            var pro_tr = jQuery(".tran_pro_clone").clone();
            pro_tr.removeClass('tran_pro_clone').addClass('pro_tr');
            var pro = data[i];
            pro_tr.children('.__no').text(pro.no);
            pro_tr.children('.__category').text(pro.category);
            pro_tr.children('.__price').text(pro.unitPrice);
            pro_tr.children('.__quantity').text(pro.quantity);
            pro_tr.children('.__temp').text(pro.temp);
            pro_tr.children('.__name').text(pro.name);
            pro_tr.appendTo(show_pro_body);
        }
    }

    jQuery('.by_class').on('click', 'a', (function () {
            jQuery(this).parent().addClass('active').siblings().removeClass('active');
            var class_no = jQuery(this).attr('val');
            page.currentPage = 0;
            if (class_no === 'a') {
                reload();
                return;
            }
            search.prev('input[type=text]').val(class_no);
            search.trigger('click');
        })
    );


    /**
     *  .nextPage的click监听
     *   显示下一页
     */
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

    /**
     *  .prevPage的click监听
     *   显示上一页
     */
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


    /**
     * 分页函数
     * @param star 开始index
     * @param end  结束index
     */
    function showPro(star, end) {

        jQuery('.pro').remove();
        if (star!==0){
            var i0 = 0;
            for (var j = star; j <= end; j++) {
                if (!(data[j])) {
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

    /**
     * 页面product显示函数
     * @param i1 data的index
     */
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
        pro1.appendTo(proBody);
    }

    function showTable() {
        jQuery('.t_pro').remove();
        for (var i = 0;i<data.length;i++){
            if ( data[i].temp !== 0){
                var pro = jQuery(".tr_pro.pro0").clone();
                pro.addClass('t_pro');
                pro.removeClass('pro0');
                pro.find('.t_no').html(data[i].no);
                pro.find('.t_name').html(data[i].name);
                pro.find('.t_quantity').html(data[i].quantity);
                pro.find('.i_temp').val(data[i].temp).attr('name', data[i].no);
                pro.appendTo('.temp_body');
            }
        }
    }

    /*
     * 出入库操作
     */
    proBody.on('click', '.thumbnail', function () {
        var thumbnail = jQuery(this);
        var index = thumbnail.find('.pro_index').text();
        index = parseInt(index);
        var tempVal = parseInt(data[index].temp);
        tempVal = tempVal + 1;
        data[index].temp = tempVal;
        showTable();

    });

    proBody.on('click', '.add', function (event) {
        var thumbnail= jQuery(this).parent().parent('.product');
        var index = thumbnail.find('.pro_index').text();
        data[index].temp += 1;
        showTable();
        event.stopPropagation();
    });

    proBody.on('click', '.sub', function (e) {

        var thumbnail= jQuery(this).parent().parent('.product');
        var index = thumbnail.find('.pro_index').text();
        data[index].temp -= 1;
        if (data[index].temp < 0) {
            data[index].temp = 0;
        }
        showTable();
        e.stopPropagation()
    });
});

