package com.bridgelabz.addressbook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class AddressBookFileIO {
	public static String addressBookFileName="addressBookFile";
	public void writeData(List<Contacts> contactList,String fileName) {
		StringBuffer empBuffer=new StringBuffer();
		contactList.forEach(contact->{
			String contactDataString=contact.toString().concat("\n");
			empBuffer.append(contactDataString);
		});
		try {
			Files.write(Paths.get(addressBookFileName+fileName+".txt"),empBuffer.toString().getBytes());
			} catch(IOException e) {
				e.printStackTrace();
		}
	}
	public List<String> readData(String fileName) {
		List<String> contactList=new ArrayList<>();
		try {
			Files.lines(new File(addressBookFileName+fileName+".txt").toPath())
			.map(line->line.trim())
			.forEach(line->contactList.add(line));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return contactList;
	}
	public void printdata(String fileName) {
		try {
			Files.lines(new File(addressBookFileName+fileName+".txt").toPath())
			.forEach(System.out::println);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
