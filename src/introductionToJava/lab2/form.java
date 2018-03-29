package introductionToJava.lab2;

public class form implements custrec_if {
	private String name = null;
	private String addr = null;
	
//	@Override
	public String getaddr() {
		// TODO Auto-generated method stub
		return addr;
	}
//	@Override
	public String getname() {
		// TODO Auto-generated method stub
		return name;
	}
//	@Override
	public void setaddr(String s) {
		// TODO Auto-generated method stub
		addr = s;
	}
//	@Override
	public void setname(String s) {
		// TODO Auto-generated method stub
		name = s;
	}
	public static void main( String[] arg){
		form f = new form();
		f.setname("james");
		System.out.println("Name is: "+ f.getname());
	}// end main method
	
}
