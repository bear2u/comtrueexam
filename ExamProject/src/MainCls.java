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
 * �� ������ ���� ���α׷�


���� ������ ������ȣ(����3�ڸ�), �̸�(�ѱ�2~4��), ��ȭ��ȣ, �̸��� �ּҰ� ���Ϸ� ����Ǿ�� �ϸ�,
���� ����� �����ϴ� ���α׷��� ����ø� �˴ϴ�.


1.	���� ���� ���
2.	���� ����Ʈ ��� (��� ���� : ���� ��ȣ, �̸�, ��ȭ��ȣ, �̸��� / �̸����� �����Ͽ� ���)
3.	���� �˻� (�̸�, ��ȭ��ȣ, ��ȭ��ȣ, �̸����ּ� �� �ʵ庰 �˻�)
4.	���� ���� (���� ���� �ش� ���� ���� ����)



������ ���� ���� 
- ������ȣ�� ������ ���� ���� 
- �ش� ���α׷��� ����ڰ� exit ����� �Է��ϸ� ���� 
- �ʵ庰 �Է� ���Ŀ� ���� ������ ���� ����� ���� ���� 
- ��ȭ��ȣ�� �̸��� �ּҴ� ����ǥ������ �̿��Ͽ� ���Ͽ� �´� ���� �Է� ���� �� �ֵ��� ó�� 
- C ���α׷��� ����ǥ���� ���� : http://gnuwin32.sourceforge.net/packages/regex.htm
- JAVA ���α׷� ����ǥ���� ���� : java.util.regex package 

 * 
 *
 */
public class MainCls {
	public static final String _FILE_NAME = "member.dat";
	String fileName = "";

	public static void main(String[] args) {
				
		//Handler ��� (MainCls ���� ����� �Լ��� ���)
		MainCls handler = new MainCls();
		handler.outMemu(); // ���Ͻô� ����� �����ϰԲ� ���
		
		Scanner scanner = new Scanner(System.in);
		String message = null;
		
		//���� ����Ʈ�� �ϴ� �����ɴϴ� 
		ArrayList<MemberData> memberList = handler.fetchMemberList();
		//�켱 ���Ϸ� ����� ȸ������Ʈ ������ �����ؼ� ����ϴ�.
		while(!(message = scanner.nextLine()).equalsIgnoreCase("5")){
			
			
			if(message.equalsIgnoreCase("1")){ //���ڼ��� //�������
				
				MemberData member = new MemberData(); 
				System.out.println("����Ͻ� ���� ��ȣ�� �Է����ּ���.");
				String member_no = null;
				while( !handler.checkMemberNo(member_no = scanner.nextLine())){ //������ȣ �˻��ؼ� �߸��Ǹ� ������ �Է¹޵��� «
					System.out.println("�߸��� ������ȣ�Դϴ�.3�ڸ����ڸ� �Էµ˴ϴ�.�ٽ��Է����ּ���.");
				}
				member.setNo(member_no);
				System.out.println("����Ͻ� ���� �̸��� �Է����ּ���.");
				String member_name = null;
				while( !handler.checkMemberName(member_name = scanner.nextLine())){ //�����̸� �˻��ؼ� �߸��Ǹ� ������ �Է¹޵��� «
					System.out.println("�߸��� �����̸��Դϴ�.�ִ� 4�ڸ����ڱ����� �Էµ˴ϴ�.�ٽ��Է����ּ���.");
				}
				member.setName(member_name);
				System.out.println("����Ͻ� ���� ��ȭ��ȣ�� �Է����ּ���.");
				String member_phone_number = null;
				while( !handler.checkMemberPhone(member_phone_number = scanner.nextLine())){//������ȭ��ȣ �˻��ؼ� �߸��Ǹ� ������ �Է¹޵��� «
					System.out.println("�߸��� ����ȣ�Դϴ�.'-'�� �������ּ���.�ٽ��Է����ּ���.");
				}
				member.setPhone(member_phone_number);
				System.out.println("����Ͻ� ���� �̸��ϸ� �Է����ּ���.");
				String member_email = null;
				while( !handler.checkMemberEmail(member_email= scanner.nextLine())){//ȸ���̸��� �˻��ؼ� �߸��Ǹ� ������ �Է¹޵��� «
					System.out.println("�߸��� �̸��� �ּ��Դϴ�.�ٽ��Է����ּ���.");
				}
				member.setEmail(member_email);			
						
				boolean result = handler.addMember(memberList , member);
				if(result){
					System.out.print("���� ��ϿϷ�ƽ��ϴ�.");
					
				}
			}else if(message.equalsIgnoreCase("2")){	 //��ϵ� �������		
				handler.outMemberList(memberList);
			}else if(message.equalsIgnoreCase("3")){			//�˻����� ���
				System.out.println("1. ���� ��ȣ �˻�");
				System.out.println("2. ���� �̸� �˻�");
				System.out.println("3. ���� ��ȭ��ȣ �˻�");
				System.out.println("4. ���� �̸��� �˻�");
				System.out.println("5. ������");
				
				String command = scanner.nextLine();
				if(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4")){
					System.out.println("�˻��Ͻ� ������ �Է����ּ���.");
					String searchContent = scanner.nextLine();
					ArrayList<MemberData> resultList = handler.searchMember(memberList, Integer.parseInt(command), searchContent);
					handler.outMemberList(resultList);
				}else{
					System.out.println("�ý����� �����մϴ�.");
				}
			}else if(message.equalsIgnoreCase("4")){
				handler.outMemberListSimple(memberList);
				System.out.println("�����Ͻ� ���� ��ȣ�� �Է����ּ���.");
				String no = scanner.nextLine();
				memberList = handler.deleteMember(memberList,no);				
				System.out.println("������ �Ϸ�ƽ��ϴ�.");
				handler.outMemberList(memberList);
			}else{
				System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ��Է����ּ���.");
			}
						
		}
		
		System.out.println("�ý����� �����մϴ�.");
		
	}
	public void outMemu(){
		System.out.println("���Ͻô� ��ȣ�� �Է����ּ���.");
		System.out.println("1. ���� ���� ���");
		System.out.println("2. ���� ����Ʈ ���");
		System.out.println("3. ���� �˻�");
		System.out.println("4. ���� ����");
		System.out.println("5. ������");
	}
	/* �̸��� üũ */
	public boolean checkMemberEmail(String email){		
		Pattern VALID_EMAIL_ADDRESS_REGEX = 
			    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				//���Խ��� ���ͳݿ��� ã�ƺ��� �����̴ϴ�.
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        
		return matcher.find();
	}
	/*������ȭ ��ȣ �˻�*/
	public boolean checkMemberPhone(String no){
		boolean result = false;		
		if(no.matches("\\d{11,12}")){ //���Խ��� ���� ���ͳݿ��� ���°Ŷ�...
			result = true;
		}
		return result;
	}
	/* ���� ��ȣ �˻� */
	public boolean checkMemberNo(String no){
		boolean result = false;
		if(no.length() == 3){ 
			result = true;
		}else if(no.matches("[-+]?\\d*\\.?\\d+")){
			result = true;
		}
		return result;
	}
	/* �̸��� ��ȿ�� ����*/
	public boolean checkMemberName(String no){
		boolean result = false;
		if(no.length() <= 4){ //4�ڸ� ����
			result = true;
		}
		return result;
	}
	/*�����ϰ� ����ϱ� ���� �Լ�*/
	public void outMemberListSimple(ArrayList<MemberData> list){
		for(MemberData data : list){			
			System.out.println("������ȣ :" + data.getNo() + "|" + "�����̸�:" + data.getName());						
		}
	}
	/* �Ϲ������� ����ϱ� ���� �޼ҵ�*/
	public void outMemberList(ArrayList<MemberData> list){
		for(MemberData data : list){
			System.out.println("==============================");
			System.out.println("������ȣ : " + data.getNo());
			System.out.println("�����̸� : " + data.getName());
			System.out.println("��ȭ��ȣ : " + data.getPhone());
			System.out.println("�̸���   : " + data.getEmail());
			System.out.println("==============================");
		}
	}
	//ȸ���߰� �Լ�
	public boolean addMember(ArrayList<MemberData> list,MemberData member){
		boolean result = false;
		try {						
			list.add(member);
			list = arrayListArrange(list); //arrange list;		//ȸ�� ����Ʈ ����	
			FileWriter fw = new FileWriter(_FILE_NAME,true); //the true will append the new data //������ ��������			
			for(MemberData data : list){ //����Ʈ ���鼭 �����͸� �����
				String txt = data.getNo() + "|" + data.getName() + "|" + data.getPhone() + "|" + data.getEmail(); //���Ͽ� �ֱ����� �ؽ�Ʈ���� ����	
				fw.write( txt + "\n");//appends the string to the file
			}		    		    
		    fw.close();
		    result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	//��������Ʈ�� �����ϱ� ���� �޼ҵ� (�̸��� �������� ��������)
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
			  //�����ϱ� ���� ����� ���ڰ�1�� �־������̰� �ٸ����� ���� ����ΰ� ����
		   return collator.compare(object1.getName(), object2.getName());
		
		  }
	};

	
	public ArrayList<MemberData> fetchMemberList(){		
		
		ArrayList<MemberData> list = new ArrayList<MemberData>();
		try {
			File f = new File(_FILE_NAME);		//���ϻ���
			f.createNewFile(); //������ ������ ����
			BufferedReader br = new BufferedReader(new FileReader(new File(_FILE_NAME))); //�����б� ���� ��Ʈ�� ����			
			String value = null;
//			System.out.println("����|��ȣ|�̸�|����ȣ|�̸���|");
			while((value = br.readLine()) != null){
				MemberData member = new MemberData(); 
				String[] array = value.split("\\|"); //���Ͽ� ����� CSV���� �ؽ�Ʈ���� |�� �� ¥���ϴ�.
				member.setNo(array[0]);
				member.setName(array[1]);
				member.setPhone(array[2]);
				member.setEmail(array[3]);
				list.add(member); //�׸��� �����迭�� ������ �ٽ� ���������� ����Ʈ�� �ֽ��ϴ�.
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<MemberData> searchMember(ArrayList<MemberData> memberList,int number,String searchContent){
		ArrayList<MemberData> searchedMemberList = new ArrayList<MemberData>(); //�˻�����Ʈ�� ������ ��������
		for(MemberData data : memberList){
			switch(number){
			case 1:
				if(data.getNo().contains(searchContent)){ //�˻������� ��ȣ�� ��ġ�ϸ� �˻�����Ʈ�� ����ϴ�.
					searchedMemberList.add(data);
				}
				break;
			case 2:
				if(data.getName().contains(searchContent)){ //�̸��˻��ؼ� ������ ����
					searchedMemberList.add(data);
				}
				break;
			case 3:
				if(data.getPhone().contains(searchContent)){ //���˻��ؼ� ������ ����
					searchedMemberList.add(data);
				}
				break;
			case 4:
				if(data.getEmail().contains(searchContent)){ //�̸��� �˻��ؼ� ������ ����
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
				tmpList.remove(data); //��ȣ�˻��ؼ� ������� ���� ��, ���θ���Ʈ�� �ϰԵǸ� �����̱� ������ �����ؼ� �ӽø���Ʈ�� ����
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
 * �����͸� ������� Ŭ����
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
