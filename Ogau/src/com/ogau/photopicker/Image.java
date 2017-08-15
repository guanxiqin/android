package com.ogau.photopicker;

public class Image {
	public String path;
	public String name;
	public long time;

	public Image(String path, String name, long time) {
		super();
		this.path = path;
		this.name = name;
		this.time = time;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		try {
			Image other = (Image) obj;
			return this.path.equalsIgnoreCase(other.path);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		return super.equals(obj);
	}
}
