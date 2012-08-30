package cr4.items;

public class HI {

	int text_id;
	String text;
	int position;
	String item;
	long created_at;
	
	
	public HI(	int text_id, String text, int position, String item, long created_at) {
		
		this.text_id = text_id;
		this.text = text;
		this.position = position;
		this.item = item;
		this.created_at = created_at;
		
	}//public HisItem(	int text_id, String text, int position, String item, long created_at)


	public int getText_id() {
		return text_id;
	}


	public String getText() {
		return text;
	}


	public int getPosition() {
		return position;
	}


	public String getItem() {
		return item;
	}


	public long getCreated_at() {
		return created_at;
	}


	public void setText(String text) {
		this.text = text;
	}


	public void setItem(String item) {
		this.item = item;
	};
	
	
	
}//public class HisItem

