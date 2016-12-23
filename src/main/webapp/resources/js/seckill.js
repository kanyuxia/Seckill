// 存放主要交互逻辑js代码
// javascript 模块化

var seckill = {
	//封装秒杀相关ajax的url
	URL:{
		now:function(){
			return '/seckill/time/now';
		},
		exposer:function(seckillId){
			return '/seckill/'+seckillId+'/exposer';
		},
		execution:function(seckillId,md5){
			return '/seckill/'+seckillId+'/'+md5+'/execution';
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
	handleSeckill:function(seckillId,md5,node){
		//获得秒杀地址
		var killUrl = seckill.URL.execution(seckillId,md5);
		console.log("killUrl:"+killUrl);
		//绑定一次点击事件
		$('#killBtn').one('click',function(){
			//执行秒杀请求
			//1:先禁用按钮
			$('#killBtn').addClass('disabled');
			//2:发送ajax请求
			$.post(killUrl,{},function(result){
				console.log(result);
				if(result && result['success']){
					var SeckillExecution = result['data'];
					var state = SeckillExecution['state'];
					var stateInfo = SeckillExecution['stateInfo'];
					//3.显示秒杀结果
					node.html('<span class="label label-success">'+stateInfo+'</span>');
				}
			});
		});
		node.show();
	},
	//执行获取秒杀接口地址函数
	handleSeckillKill:function(seckillId,node){
		//获取秒杀地址，控制显示逻辑，执行秒杀
		node.hide()
			.html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		// 通过ajax请求MD5
		console.log(seckill.URL.exposer(seckillId));
		$.post(seckill.URL.exposer(seckillId),{},function(result){	//成功回调函数 result为返回数据
			// 执行交互流程
			if(result && result['success'] == true){
				var exposer = result['data'];
				if(exposer['exposed']){
					//获得md5
					var md5 = exposer['md5'];
					//开启秒杀
					seckill.handleSeckill(seckillId,md5,node);
				}else{
					//未开启秒杀
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					//重新进入计时逻辑
					seckill.countdown(seckillId,now,start,end);
				}
				
			}
		});
		
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
				var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on('finish.countdown',function(){ //时间完成后回调事件
				//获取秒杀接口地址
				seckill.handleSeckillKill(seckillId,seckillBox);
			});
		}else{
			//获取秒杀接口地址
			seckill.handleSeckillKill(seckillId,seckillBox);
		}
	},
	//详情页秒杀逻辑
	detail:{
		// 详情页初始化
		init:function(params){
			//手机验证和登录,计时交互
			
			//在cookie中查找手机号码
			var killPhone = $.cookie('userPhone');
			var seckillId = params['seckillId'];
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			console.log(endTime);
			//验证手机号码
			if(!seckill.validatePhone(killPhone)){
				//绑定手机号码
				//控制输出
				var KillPhoneModal = $('#KillPhoneModal');
				console.log(KillPhoneModal);
				KillPhoneModal.modal({
					show:true,//显示弹出层
					backrop:'static',//禁止位置关闭
					keyboard:false,//关闭键盘事件
				});
				$('#KillPhoneBtn').click(function(){
					var inputPhone = $('#KillPhoneKey').val();
					if(seckill.validatePhone(inputPhone)){
						//写入cookie
						$.cookie('userPhone',inputPhone,{expires:7,path:'/seckill'});
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
					console.log(nowTime);
					//调用计时函数
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log('result:'+result);
				}
			});
			
		}
	
	}
}