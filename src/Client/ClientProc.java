package Client;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class ClientProc { 
	
	ClientDAO dao;
	
	public ClientProc() {
		dao = new ClientDAO();
	}
	

	public void showClientList(){
	
		List<ClientDTO> list = dao.getClientList();
		
		System.out.println("                             Client List");
		System.out.println("============================================================================");
		if(list!=null&&list.size()>0){			
			System.out.println("reg.No\t  id \t\t��й�ȣ\t\t�̸�\t\t����\t\t�ּ�\t\t��ȭ��ȣ\t\t����Ʈ\t\t���� Ƚ��");
			System.out.println("============================================================================");
			
			for (ClientDTO dto : list){
				System.out.println("\t" + dto.getClient_id() + "\t\t"+ dto.getClient_password() + "\t\t"+ dto.getClient_name()+ "\t\t"+ dto.getClient_birth()+ "\t\t"+ dto.getClient_address()+ "\t\t"+ dto.getClient_number()+ "\t\t"+ dto.getClient_point()+ "\t\t"+ dto.getClient_purchase_count());
			}			
	
		}else{
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "+((list==null)?"0":list.size())+"��=\n");
	}

	
	public String reInput(){
		Scanner scn = new Scanner(System.in);
		String str="";
		while(true){
			str = scn.nextLine();
			if(!(str==null || str.trim().equals(""))){
				break;
			}else{
				System.out.println("������ �Է��ϽǼ������ϴ�. �ùٸ����� �Է����ּ���!");
				System.out.print("��");
			}
		}
		
		return str;
	}

}
