package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092100564758";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8joDsfF05ux8ScGXfsjZHaUZRYaa+s3qLrs9qAbp9jW4tfuzV/0qX2DrLbgMNpgr0hwY6JefxuZPhJlTq2kx8ImjEVnbexhe9MQ3z2XfRJzH0bEwcWHydSMGAoyTbxXc5ZwYqOe3nYQoqbMKydqgcLwOOQUcnrgN+IidiC56+b+OZSqjqhEJszbOuANaZm8HEE5gXfa4KOSusyosOXf8gDXhe0ULwgojv1jxWePksoa+H4DXafAxbRPotL2Fk/z3ivJ1xu1oc5Ade+OLX4CSJ25X8RaAHBPjlddl19Q92WGT11X+JIDGhDWb6gPyJ+oQqPDn0CkIpNkDlhaGDcSVdAgMBAAECggEATpgDiw8neKnTgMcS35QT5d4urjtK2pBLEzARSnlf93ddJl+SbnyT5gj+MM8xPJ4qUNR7Nv+X13zn8+AbYy0M1m0tSDFWKPNe78AjtbG+rJJu0xfU/bewcYrjBTB7a5O6Z1XXDwDIdjpPl3FwEh09Y0oBbGkvNI3m7MtlqQt45p1Zodu0p+MFDDeC32VIK0Qz/KFCNPt3EhGd58C0jumrpg8Mbf/RJvJb6rUX8l5J2qnFsR4d6IOWhizCNt0Ud8XXGqY/6TWMlFrwa5uwfS++rxP5SgBEIhM5ck/r4Bh78Kdqc747dejBkJIMu/c/7RKuqusH/9jyJkpNG0UF7bZkTQKBgQD+JJwN6gEiyEqhsJ78pzwtTD26ixcHn0Kghx5RnwoEtPH24AdrqiuFIOB6GkUaUK7mlEPiRnTdzU5cKy2MpZZ0Mq6RqxbXVL1shYaPjhzKQdyrfx3gkSzz4yx62wdnjVpz94DhdSZ6xUDeu5eSLIYQDdNlne1bM5jM45J4JFXdFwKBgQC97zXsqMeZ90tPLUc9kc5xHtxLt4zbY7/+GAzg7NGsGCURy+T6rq1Hx2p29ZYlms+XgkPEGGn3SxYEbpdn3LYCgJNlgz+JEivKFfT//u4KdPQupdAToau3P7tVg8Si/4lNo1/JTqXpCidd6izc4/xcXhGXYrVpysYfHZqSRy2hqwKBgQDWrJgBjNv+xI9+O1Jg+ubzIIa4D4uZjabbMKekrKHqzenh7uV1xZ3Ta60SlyQuEV9cOHuLJJbNUQJTCsoEF0+awcECHTJDHDqR88xxcMrS8oVzfAIB0r/VF91XvbJof1ebw8LjL3EcnYipXG6xM9jesL8UBC4eLtxwnf74a0D4awKBgGoPTdldVjhPsyn/aqyi4tigFvvg/zLzPJ6y514iX+LtiKWraa3iJLjUTksnUDSnggxknVq/F00UDLNk3f8NbEVxbt1csPnlv7YVFL5uqJYGQORbixmrSawGVqGl9wPeFdnYxQAwfszm8pyqy2p9lMovbTtPz2XH6WIuscXFIWIJAoGBAMzP/pLK+OfYTCqeYkPJU3eWjT46wmVgEHWE5bxdD40GPi2Rj1dkqCCble1CgiZ+jGBiXYoyoAJJzYaTtSC8NaKMPSBbkzptXuzWI2Q8CtVRQ/X9wvIb8WDiANcmEYeQ2DaH5pqJEULKitsQv5PwCZdtEWv5rBpLF4oHI95ClbeL";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1U7j3Bl5QtyF6ZGouhkUFivy4lA10SQlZFP4CBY9uigwMDljVnhhQ/8yfRCHokJ6ZYaHfRivNWxm0zjtmKCNILN8HNnPtEx7lSChPSLkbwAvCtwhgPgtQFXYDlRvRACDfLw9uetUZXP7z0sd1RH8X7LVJ0d/+KN3ODlPLCcjCWXR08uHW7ys4Yrtv2AePnntvSFNNUtGOLG3mbv+sWW14I/++10KwejhnkBaMiBkx8FLcOWvT4bjArLH8TynT4Gd66GJjbCXY1jHELvKuLguBRWL6Ya22iN1udq99GQY6l8Z5trtQhOoCCj0MiAp3V8DRlwVTvg9Hn2raTENpCiJNQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://itmayiedu.tunnel.qydev.com/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://itmayiedu.tunnel.qydev.com/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
