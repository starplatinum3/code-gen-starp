package top.starp.util;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.drive.v1.model.*;
import java.util.HashMap;
import com.lark.oapi.core.request.RequestOptions;

// SDK 使用文档：https://github.com/larksuite/oapi-sdk-java/tree/v2_main
// 开发者复制该Demo后，需要修改Demo里面的"appID", "appSecret"为自己应用的appId,appSecret.
public class ListFileSample {

	public static void main(String arg[]) throws Exception {
		// 构建client
		Client client = Client.newBuilder("appId", "appSecret")
			.disableTokenCache() //如需SDK自动管理租户Token的获取与刷新,可删除该行
			.build();

		// 创建请求对象
		// 发起请求
		// 如开启了Sdk的token管理功能，就无需调用 RequestOptions.newBuilder().tenantAccessToken("t-xxx").build()来设置租户token了

		ListFileResp resp = client.drive().file().list(ListFileReq.newBuilder().build());
//		ListFileResp resp = client.drive().file().list(RequestOptions.newBuilder()
//			.tenantAccessToken("")
//			.build());


		// 处理服务端错误
		if(!resp.success()) {
			System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
			return;
		}

		// 业务数据处理
		System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
	}
}
