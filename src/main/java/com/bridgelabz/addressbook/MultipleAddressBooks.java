package com.bridgelabz.addressbook;
//Refactor Uc16
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class MultipleAddressBooks {
	private Map<String,MultipleContacts> addressBookMap,cityMap,stateMap;
	private MultipleContacts addressb,cityb,stateb;
	Scanner sc;
	String states;
	public MultipleAddressBooks() {
		addressBookMap=new HashMap<>();
		cityMap=new HashMap<>();
		stateMap=new HashMap<>();
		cityb=null;
		stateb=null;
		sc=new Scanner(System.in);
	}
	public int displayChoice() {
		System.out.println("1.Add new Address Book");
		System.out.println("2.View Address Book List");
		System.out.println("3.Search Person By City");
		System.out.println("4.Search Person By State");
		System.out.println("5.Count Contacts by city");
		System.out.println("6.Count Contacts by State");
		System.out.println("7.Sort Contacts by Name");
		System.out.println("8.Add to Existing Address Book");
		System.out.println("9.Sort Contacts by City");
		System.out.println("10.Sort Contacts by States");
		System.out.println("11.Sort Contacts by Zip");
		System.out.println("12.Write Contacts to File");
		System.out.println("13.Read Contacts from File");
		System.out.println("14.Print Contacts from File");
		System.out.println("15.Database Operations");
		System.out.println("16.Quit");
		System.out.println("Enter choice from 1 and 16");
		int choice=sc.nextInt();
		return choice;
	}
	public void addNewAddressBook() {
		System.out.println("Enter Name For Address Book");
		String aName=sc.next();
		aName=aName.toLowerCase();
		addressb=new MultipleContacts();
		Contacts cc=addressb.addContact();
		addressBookMap.put(aName, addressb);
		if(cityMap.containsKey(cc.city)) {
			cityb.contactArrayList.add(cc);
			cityMap.put(cc.city, cityb);
		} else {
			cityb=new MultipleContacts();
			cityb.contactArrayList.add(cc);
			cityMap.put(cc.city, cityb);
		}
		if(stateMap.containsKey(cc.state)) {
			stateb.contactArrayList.add(cc);
			stateMap.put(cc.state, stateb);
		} else {
			stateb=new MultipleContacts();
			stateb.contactArrayList.add(cc);
			stateMap.put(cc.state, stateb);
		}
	}
	public void addToExistingAddressBook() {
		System.out.println("Enter Name of Address Book to View Contact");
		String adbName=sc.next();
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			if(m.getKey().equals(adbName)) {
				addressb.addContact();
				addressBookMap.put(adbName, addressb);
			}
		}
	}
	public void viewAddressBookList() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			System.out.println(m.getKey());
		}
	}
	public void searchContactByCityName() {
		System.out.println("Enter name of City to search contact");
		String city=sc.next();
		city=city.toLowerCase();
		if(cityMap.containsKey(city)) {
			MultipleContacts list=cityMap.get(city);
			list.showAllContacts();
		}
	}
	Predicate<Contacts> search=c->c.state.equalsIgnoreCase(states);
	public void searchContactByStateName() {
		System.out.println("Enter name of State to search contact");
		states=sc.next();
		states=states.toLowerCase();
		MultipleContacts nc=new MultipleContacts();
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			if(m.getValue().contactArrayList.size()>0) {
				ArrayList<Contacts> stateWiseList=(ArrayList<Contacts>) m.getValue().contactArrayList.stream()
						  	  					   .filter(search)
						  	  					   .collect(Collectors.toList());
				Iterator<Contacts> i=stateWiseList.iterator();
				while(i.hasNext()) {
					Contacts c=(Contacts) i.next();
					nc.contactArrayList.add(c);
				}
			}
		}
		nc.showAllContacts();
	}
	public void viewContactByState() {
		System.out.println("Enter name of State to search contact");
		String state=sc.next();
		state=state.toLowerCase();
		if(stateMap.containsKey(state)) {
			MultipleContacts list=stateMap.get(state);
			list.showAllContacts();
		}
	}
	public void countByCity() {
		for(Map.Entry<String,MultipleContacts> m:cityMap.entrySet()) {
			System.out.println("Count for "+m.getKey()+" is "+m.getValue().contactArrayList.size());
		}
	}
	public void countByState() {
		for(Map.Entry<String,MultipleContacts> m:stateMap.entrySet()) {
			System.out.println("Count for "+m.getKey()+" is "+m.getValue().contactArrayList.size());
		}
	}
	public void sortByName() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			System.out.println(m.getValue().contactArrayList.size());
			ArrayList<Contacts> c=(ArrayList<Contacts>) 
								   m.getValue().contactArrayList.stream()
								   .sorted(Comparator.comparing(Contacts::getFirstLastName))
								   .collect(Collectors.toList());
			System.out.println(c.size());
			for(int i=0;i<c.size();i++) {
				System.out.println(c.get(i).name);
			}
		}
	}
	public void sortByCity() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			System.out.println(m.getValue().contactArrayList.size());
			ArrayList<Contacts> c=(ArrayList<Contacts>) 
								   m.getValue().contactArrayList.stream()
								   .sorted(Comparator.comparing(Contacts::getCity))
								   .collect(Collectors.toList());
			System.out.println(c.size());
			for(int i=0;i<c.size();i++) {
				System.out.println(c.get(i).name);
			}
		}
	}
	public void sortByState() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			System.out.println(m.getValue().contactArrayList.size());
			ArrayList<Contacts> c=(ArrayList<Contacts>) 
								   m.getValue().contactArrayList.stream()
								   .sorted(Comparator.comparing(Contacts::getState))
								   .collect(Collectors.toList());
			System.out.println(c.size());
			for(int i=0;i<c.size();i++) {
				System.out.println(c.get(i).name);
			}
		}
	}
	public void sortByZip() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			System.out.println(m.getValue().contactArrayList.size());
			ArrayList<Contacts> c=(ArrayList<Contacts>) 
								   m.getValue().contactArrayList.stream()
								   .sorted(Comparator.comparing(Contacts::getZip))
								   .collect(Collectors.toList());
			System.out.println(c.size());
			for(int i=0;i<c.size();i++) {
				System.out.println(c.get(i).name);
			}
		}
	}
	public void writeAddressBookDataToFile() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			new AddressBookFileIO().writeData(m.getValue().contactArrayList,m.getKey());
		}
	}
	public void readFile() {
		List<String> contactList=null;
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			contactList=new AddressBookFileIO().readData(m.getKey());
		}
		System.out.println("Size of Address Book="+contactList.size());
	}
	public void printData() {
		for(Map.Entry<String,MultipleContacts> m:addressBookMap.entrySet()) {
			new AddressBookFileIO().printdata(m.getKey());
		}
	}
	public void performDatabaseOperation() {
		new AddressBookDb().mapAddressBookRecords(addressBookMap);
	}
	public static void main(String[] args) {
		MultipleAddressBooks mab=new MultipleAddressBooks();
		int choice;
		int flag=0;
		while(flag==0) {
			choice=mab.displayChoice();
			switch(choice) {
				case 1:
					mab.addNewAddressBook();
					break;
				case 2:
					mab.viewAddressBookList();
					break;
				case 3:
					mab.searchContactByCityName();
					break;
				case 4:
					mab.viewContactByState();
					break;
				case 5:
					mab.countByCity();
					break;
				case 6:
					mab.countByState();
					break;
				case 7:
					mab.sortByName();
					break;
				case 8 :
					mab.addToExistingAddressBook();
					break;
				case 9:
					mab.sortByCity();
					break;
				case 10:
					mab.sortByState();
					break;
				case 11:
					mab.sortByZip();
					break;
				case 12:
					mab.writeAddressBookDataToFile();
					break;
				case 13:
					mab.readFile();
					break;
				case 14:
					mab.printData();
					break;
				case 15:
					mab.performDatabaseOperation();
					break;
				case 16:
					flag=1;
					break;
				default:
					System.out.println("You have Entered Wrong Choice.Please enter option between 1 to 19");
			}
		}
	}
}
