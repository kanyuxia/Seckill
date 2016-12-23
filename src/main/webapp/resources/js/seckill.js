// 存放主要交互逻辑js代码
// javascript 模块化

var seckill = {
	//封装秒杀相关ajax的url
	URL:{
		now:function(){
			return '/seckill/time/now';
		}
	},
	//验证手机号函数
	validatePhone:function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//执行秒杀函数
	handleSeckillKill:function(){
		//处理秒杀逻辑
	},
	//计时操作函数
	countdown:function(seckillId,nowTime,startTime,endTime){
		var seckillBox = $('#seckil-box');
		//时间判断
		if(nowTime > endTime){
			//秒杀结束
			seckillBox.html('秒杀结束');
		}else if(nowTime < startTime){
			//秒杀未开始,计时是事件
			var killTime = new Date(startTime + 1000);
			seckillBox.countdown(killTime,function(event){
				//时间格式
				var format = event.strftime('秒杀倒计时:%D天  %H时  %M分  %S秒');
				seckillBox.html(format);
			}).on('finish.countdown',function(){ //时间完成后回调事件
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckillKill();
			})
		}else{
			seckill.handleSeckillKill();
		}
	},
	//详情页秒杀逻辑
	detail:{
		// 详情页初始化
		init:function(params){
			//手机验证和登录,计时交互
			
			//在cookie中查找手机号码
			var killPhone = $.cookie('killPhone');
			var sekillId = params['sekillId'];
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			//验证手机号码
			if(!seckill.validatePhone(KillPhone)){
				//绑定手机号码
				//控制输出
				var KillPhoneModal = $('#KillPhoneModal');
				KillPhoneModal.modal({
					show:true,//显示弹出层
					backrop:'static',//禁止位置关闭
					keyboard:false,//关闭键盘事件
				});
				$('KillPhoneBtn').click(function(){
					var inputPhone = $('#KillPhoneKey').val();
					if(seckill.validatePhone(inputPhone)){
						//写入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
						//刷新页面
						window.location.reload();
					}else{
						$('#KillPhoneMessage').hide().html('<label label-danger>手机号码错误!</label>').show(300);
					}
				});
			}
			//已经登录
			//计时交互
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					//调用计时函数
					seckill.countdown(sekillId,startTime,endTime)
				}else{
					console.log('result:'+result);
				}
			});
			
		}
	
	}
}