import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ■ 직원을 관리 프로그램


직원 정보는 직원번호(숫자3자리), 이름(한글2~4자), 전화번호, 이메일 주소가 파일로 저장되어야 하며,
다음 기능을 만족하는 프로그램을 만드시면 됩니다.


1.	직원 정보 등록
2.	직원 리스트 출력 (출력 형식 : 직원 번호, 이름, 전화번호, 이메일 / 이름으로 정렬하여 출력)
3.	직원 검색 (이름, 전화번호, 전화번호, 이메일주소 각 필드별 검색)
4.	직원 삭제 (직원 선택 해당 직원 정보 삭제)



구현시 참고 사항 
- 직원번호는 유일한 값을 가짐 
- 해당 프로그램은 사용자가 exit 명령을 입력하면 종료 
- 필드별 입력 형식에 맞지 않으면 직원 등록이 되지 않음 
- 전화번호와 이메일 주소는 정규표현식을 이용하여 패턴에 맞는 값만 입력 받을 수 있도록 처리 
- C 프로그램용 정규표현식 참고 : http://gnuwin32.sourceforge.net/packages/regex.htm
- JAVA 프로그램 정규표현식 참고 : java.util.regex package 

 * 
 *
 */
public class MainCls {
	public static final String _FILE_NAME = "member.dat";
	String fileName = "";

	public static void main(String[] args) {
				
		//Handler 등록 (MainCls 생성 잡다한 함수들 사용)
		MainCls handler = new MainCls();
		handler.outMemu(); // 원하시는 목록을 선택하게끔 출력
		
		Scanner scanner = new Scanner(System.in);
		String message = null;
		
		//직원 리스트를 일단 가져옵니다 
		ArrayList<MemberData> memberList = handler.fetchMemberList();
		//우선 파일로 저장된 회원리스트 정보를 갱신해서 만듭니다.
		while(!(message = scanner.nextLine()).equalsIgnoreCase("5")){
			
			
			if(message.equalsIgnoreCase("1")){ //숫자선택 //직원등록
				
				MemberData member = new MemberData(); 
				System.out.println("등록하실 직원 번호를 입력해주세요.");
				String member_no = null;
				while( !handler.checkMemberNo(member_no = scanner.nextLine())){ //직원번호 검색해서 잘못되면 무한정 입력받도록 짬
					System.out.println("잘못된 직원번호입니다.3자리숫자만 입력됩니다.다시입력해주세요.");
				}
				member.setNo(member_no);
				System.out.println("등록하실 직원 이름를 입력해주세요.");
				String member_name = null;
				while( !handler.checkMemberName(member_name = scanner.nextLine())){ //직원이름 검색해서 잘못되면 무한정 입력받도록 짬
					System.out.println("잘못된 직원이름입니다.최대 4자리숫자까지만 입력됩니다.다시입력해주세요.");
				}
				member.setName(member_name);
				System.out.println("등록하실 직원 전화번호를 입력해주세요.");
				String member_phone_number = null;
				while( !handler.checkMemberPhone(member_phone_number = scanner.nextLine())){//직원전화번호 검색해서 잘못되면 무한정 입력받도록 짬
					System.out.println("잘못된 폰번호입니다.'-'는 제거해주세요.다시입력해주세요.");
				}
				member.setPhone(member_phone_number);
				System.out.println("등록하실 직원 이메일를 입력해주세요.");
				String member_email = null;
				while( !handler.checkMemberEmail(member_email= scanner.nextLine())){//회원이메일 검색해서 잘못되면 무한정 입력받도록 짬
					System.out.println("잘못된 이메일 주소입니다.다시입력해주세요.");
				}
				member.setEmail(member_email);			
						
				boolean result = handler.addMember(memberList , member);
				if(result){
					System.out.print("직원 등록완료됐습니다.");
					
				}
			}else if(message.equalsIgnoreCase("2")){	 //등록된 직원출력		
				handler.outMemberList(memberList);
			}else if(message.equalsIgnoreCase("3")){			//검색종류 출력
				System.out.println("1. 직원 번호 검색");
				System.out.println("2. 직원 이름 검색");
				System.out.println("3. 직원 전화번호 검색");
				System.out.println("4. 직원 이메일 검색");
				System.out.println("5. 나가기");
				
				String command = scanner.nextLine();
				if(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4")){
					System.out.println("검색하실 내용을 입력해주세요.");
					String searchContent = scanner.nextLine();
					ArrayList<MemberData> resultList = handler.searchMember(memberList, Integer.parseInt(command), searchContent);
					handler.outMemberList(resultList);
				}else{
					System.out.println("시스템을 종료합니다.");
				}
			}else if(message.equalsIgnoreCase("4")){
				handler.outMemberListSimple(memberList);
				System.out.println("삭제하실 직원 번호를 입력해주세요.");
				String no = scanner.nextLine();
				memberList = handler.deleteMember(memberList,no);				
				System.out.println("삭제가 완료됐습니다.");
				handler.outMemberList(memberList);
			}else{
				System.out.println("잘못된 번호입니다. 다시입력해주세요.");
			}
						
		}
		
		System.out.println("시스템을 종료합니다.");
		
	}
	public void outMemu(){
		System.out.println("원하시는 번호을 입력해주세요.");
		System.out.println("1. 직원 정보 등록");
		System.out.println("2. 직원 리스트 출력");
		System.out.println("3. 직원 검색");
		System.out.println("4. 직원 삭제");
		System.out.println("5. 나가기");
	}
	/* 이메일 체크 */
	public boolean checkMemberEmail(String email){		
		Pattern VALID_EMAIL_ADDRESS_REGEX = 
			    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				//정규식은 인터넷에서 찾아보고 넣은겁니다.
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        
		return matcher.find();
	}
	/*직원전화 번호 검색*/
	public boolean checkMemberPhone(String no){
		boolean result = false;		
		if(no.matches("\\d{11,12}")){ //정규식은 저도 인터넷에서 따온거라...
			result = true;
		}
		return result;
	}
	/* 직원 번호 검색 */
	public boolean checkMemberNo(String no){
		boolean result = false;
		if(no.length() == 3){ 
			result = true;
		}else if(no.matches("[-+]?\\d*\\.?\\d+")){
			result = true;
		}
		return result;
	}
	/* 이름를 유효성 검토*/
	public boolean checkMemberName(String no){
		boolean result = false;
		if(no.length() <= 4){ //4자리 이하
			result = true;
		}
		return result;
	}
	/*간단하게 출력하기 위한 함수*/
	public void outMemberListSimple(ArrayList<MemberData> list){
		for(MemberData data : list){			
			System.out.println("직원번호 :" + data.getNo() + "|" + "직원이름:" + data.getName());						
		}
	}
	/* 일반적으로 출력하기 위한 메소드*/
	public void outMemberList(ArrayList<MemberData> list){
		for(MemberData data : list){
			System.out.println("==============================");
			System.out.println("직원번호 : " + data.getNo());
			System.out.println("직원이름 : " + data.getName());
			System.out.println("전화번호 : " + data.getPhone());
			System.out.println("이메일   : " + data.getEmail());
			System.out.println("==============================");
		}
	}
	//회원추가 함수
	public boolean addMember(ArrayList<MemberData> list,MemberData member){
		boolean result = false;
		try {						
			list.add(member);
			list = arrayListArrange(list); //arrange list;		//회원 리스트 정렬	
			FileWriter fw = new FileWriter(_FILE_NAME,true); //the true will append the new data //파일을 쓰기위함			
			for(MemberData data : list){ //리스트 돌면서 데이터를 꺼집어냄
				String txt = data.getNo() + "|" + data.getName() + "|" + data.getPhone() + "|" + data.getEmail(); //파일에 넣기위한 텍스트구문 생성	
				fw.write( txt + "\n");//appends the string to the file
			}		    		    
		    fw.close();
		    result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	//직원리스트를 정렬하기 위한 메소드 (이름을 기준으로 순차정렬)
	public ArrayList<MemberData> arrayListArrange(ArrayList<MemberData> list){
		ArrayList<MemberData> sortList = new ArrayList<MemberData>();
		for(MemberData data : list){
			sortList.add(data);
		}
		Collections.sort(sortList, myComparator);
		
		return sortList;
		
	}
	private final static Comparator<MemberData> myComparator= new Comparator<MemberData>() {

        private final Collator   collator = Collator.getInstance();
		  @Override
		
		  public int compare(MemberData object1,MemberData object2) {
			  //정렬하기 위한 컴페어 인자값1이 주어진것이고 다른것은 비교할 대상인것 같음
		   return collator.compare(object1.getName(), object2.getName());
		
		  }
	};

	
	public ArrayList<MemberData> fetchMemberList(){		
		
		ArrayList<MemberData> list = new ArrayList<MemberData>();
		try {
			File f = new File(_FILE_NAME);		//파일생성
			f.createNewFile(); //파일이 없을시 생성
			BufferedReader br = new BufferedReader(new FileReader(new File(_FILE_NAME))); //파일읽기 위한 스트림 생성			
			String value = null;
//			System.out.println("순서|번호|이름|폰번호|이메일|");
			while((value = br.readLine()) != null){
				MemberData member = new MemberData(); 
				String[] array = value.split("\\|"); //파일에 저장된 CSV형태 텍스트들을 |로 다 짜릅니다.
				member.setNo(array[0]);
				member.setName(array[1]);
				member.setPhone(array[2]);
				member.setEmail(array[3]);
				list.add(member); //그리고 직원배열에 넣은걸 다시 전역변수인 리스트에 넣습니다.
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<MemberData> searchMember(ArrayList<MemberData> memberList,int number,String searchContent){
		ArrayList<MemberData> searchedMemberList = new ArrayList<MemberData>(); //검색리스트를 저장할 변수지정
		for(MemberData data : memberList){
			switch(number){
			case 1:
				if(data.getNo().contains(searchContent)){ //검색문구가 번호랑 일치하면 검색리스트에 담습니다.
					searchedMemberList.add(data);
				}
				break;
			case 2:
				if(data.getName().contains(searchContent)){ //이름검색해서 데이터 담음
					searchedMemberList.add(data);
				}
				break;
			case 3:
				if(data.getPhone().contains(searchContent)){ //폰검색해서 데이터 담음
					searchedMemberList.add(data);
				}
				break;
			case 4:
				if(data.getEmail().contains(searchContent)){ //이메일 검색해서 데이터 담음
					searchedMemberList.add(data);
				}
				break;
			}
		}
		return searchedMemberList;
	}
	public ArrayList<MemberData> deleteMember(ArrayList<MemberData> memberList,String no){
		ArrayList<MemberData> tmpList = new ArrayList<MemberData>(memberList);
			
		for(MemberData data : memberList){
			if(data.getNo().equals(no)){
				tmpList.remove(data); //번호검색해서 맞을경우 삭제 함, 메인리스트에 하게되면 동적이기 때문에 위험해서 임시리시트에 삭제
			}
		}
		return tmpList;
	}
	
	public static boolean isMatched(String message){
		boolean result = true;
		
		if(message == "1"){
			result = false;
		}else if(message == "2"){
			result = false;
		}else if(message == "3"){
			result = false;
		}else if(message == "4"){
			result = false;
		}
		
		return result;
	}
	
	

}
/**
 * 데이터를 담기위한 클래스
 * @author tommy
 *
 */
class MemberData {
	private String no;
	private String name;
	private String phone;
	private String email;
	
	public MemberData(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
}
