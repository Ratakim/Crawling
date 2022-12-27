package crawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainApp {

	public static String getCurrentData() {

		SimpleDateFormat sted = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		return sted.format(new Date());
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		// 1. 시작 시간 체크
		System.out.println(" 시작 : " + getCurrentData());

		// 2. 가져올 HTTP 주소 넣기
		HttpPost http = new HttpPost("https://www.naver.com/");

		// 3. 클라이언트 객체 생성
		HttpClient httpClient = HttpClientBuilder.create().build();

		// 4. 데이터를 Response에 담음
		HttpResponse response = httpClient.execute(http);

		// 5. Response 받은 데이터 중, DOM(문서 객체 모델) 데이터를 가져와 Entity(http header(본문에 대한 정보 및 요청/응답)와 http body를 담은 클래스)에 담음
		HttpEntity entity = response.getEntity();

		// 6. 데이터의 컨텐트 타입을 담고 Charset을 가져옴
		ContentType contentType = ContentType.getOrDefault(entity);		

		Charset charset = contentType.getCharset();

		// 7. 데이터를 한 줄씩 읽기 위해 BufferedReader에 담기, byte타입으로 읽어지는 값을 char 타입으로 처리한뒤 String 즉 문자열로 저장할 수 있게 한다는 의미 
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

		// 8. 가져온 데이터를 담을 StringBuffer, 그냥 String을 쓰면 값이 현저하게 느려짐
		StringBuffer sb = new StringBuffer();
		
		String line = "";
		
		// 9. br 내용 한 줄씩 읽어 들이는데 null이 아닐시 
		while ((line = br.readLine()) != null) {

			//.append -> 값 뒤에 붙힘
			sb.append(line + "\n");

		}

		// 10. 가져온 데이터 값을 출력
	    System.out.println(sb.toString());

		//Jsoup으로 파싱
		//Document doc = Jsoup.parse(sb.toString()); 
		 
		//Connect 처리		
		//Document doc2 = Jsoup.connect("https://www.naver.com/").get();
	
	    //System.out.println(doc2.data());

		// 11. 끝 시간 체크
		System.out.println(" 끝 : " + getCurrentData());

	}

}