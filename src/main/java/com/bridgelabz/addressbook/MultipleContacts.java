package com.bridgelabz.addressbook;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;
public class MultipleContacts {
	ArrayList<Contacts> contactArrayList;
	Scanner scanner;
	String firstName;
	public MultipleContacts() {
		contactArrayList=new ArrayList<>();
		scanner=new Scanner(System.in);
	}
	public int displayMenu() {
		System.out.println("1.Add Contact");
		System.out.println("2.Edit Contact");
		System.out.println("3.Search Contact Details");
		System.out.println("4.Show All Contact");
		System.out.println("5.Delete Contact");
		System.out.println("6.Quit");
		System.out.println("Enter Your Choice from 1 to 6");
		return scanner.nextInt();
	}
	Predicate<Contacts> isDuplicate=c->c.firstLastName.equalsIgnoreCase(firstName);
	public Contacts addContact() {
		System.out.println("Enter Below Details to create new Contact");
		System.out.println("Enter Full Name");
		firstName=scanner.nextLine();
		Contacts c=null;
		if(contactArrayList.size()>0) {
			boolean check=contactArrayList.stream()
						  .anyMatch(isDuplicate);
			if(check) {
				System.out.println("You Already have this contacts in address book");
			} else {
				c=getUserInput(firstName);
				contactArrayList.add(c);
				System.out.println("Contact is Successfully Added to AddressBook");
			}
		} else {
			c=getUserInput(firstName);
			contactArrayList.add(c);
			System.out.println("Contact is Successfully Added to AddressBook");
		}
		return c;
	}
	public Contacts getUserInput(String firstLastName) {
		System.out.println("Enter Address");
		String address=scanner.nextLine();
		System.out.println("Enter City");
		String city=scanner.nextLine();
		System.out.println("Enter State");
		String state=scanner.nextLine();
		System.out.println("Enter Zip");
		long zip=scanner.nextLong();
		System.out.println("Enter Phone Number");
		long phoneNum=scanner.nextLong();
		System.out.println("Enter Email");
		scanner.nextLine();
		String email=scanner.nextLine();
		Contacts newContact=new Contacts(firstLastName,address,city,state,zip,phoneNum,email);
		return newContact;
	}
	public void showContact() {
		int i=searchContact();
		if(i>=0) {
			System.out.println("Name="+contactArrayList.get(i).firstLastName);
			System.out.println("Address="+contactArrayList.get(i).address);
			System.out.println("City="+contactArrayList.get(i).city);
			System.out.println("State="+contactArrayList.get(i).state);
			System.out.println("Zip="+contactArrayList.get(i).zip);
			System.out.println("Mobile Number="+contactArrayList.get(i).phoneNum);
			System.out.println("Email"+contactArrayList.get(i).email);
		}
	}
	public void showAllContacts() {
		for(int i=0;i<contactArrayList.size();i++) {
			System.out.println("Contact "+(i+1)+" Detalis");
			System.out.println("Name="+contactArrayList.get(i).firstLastName);
			System.out.println("Address="+contactArrayList.get(i).address);
			System.out.println("City="+contactArrayList.get(i).city);
			System.out.println("State="+contactArrayList.get(i).state);
			System.out.println("Zip="+contactArrayList.get(i).zip);
			System.out.println("Mobile Number="+contactArrayList.get(i).phoneNum);
			System.out.println("Email="+contactArrayList.get(i).email);
		}
	}
	public int searchContact() {
		int index=-1;
		System.out.println("Enter Name of person");
		scanner.nextLine();
		String name=scanner.nextLine();
		for(int i=0;i<contactArrayList.size();i++) {
			if(name.equalsIgnoreCase(contactArrayList.get(i).firstLastName)) {
				index=i;
			}
		}
		if(index==-1) {
			System.out.println("Contact Details are not present in address book");
		}
		return index;
	}
	public void editContact() {
		int i=searchContact();
		if(i>=0) {
			System.out.println("Enter the field you want to change");
			System.out.println("1.Address");
			System.out.println("2.City");
			System.out.println("3.State");
			System.out.println("4.Zip");
			System.out.println("5.Phone Number");
			System.out.println("6.Email");
			System.out.println("Choose Option from 1 to 6");
			int choice=scanner.nextInt();
			switch(choice) {
				case 1:
					System.out.println("Enter New Address of "+contactArrayList.get(i).firstLastName);
					scanner.nextLine();
					contactArrayList.get(i).address=scanner.nextLine();
					System.out.println("Address of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;

				case 2:
					System.out.println("Enter New City of "+contactArrayList.get(i).firstLastName);
					scanner.nextLine();
					contactArrayList.get(i).city=scanner.nextLine();
					System.out.println("City of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;
				case 3:
					System.out.println("Enter New State of "+contactArrayList.get(i).firstLastName);
					scanner.nextLine();
					contactArrayList.get(i).state=scanner.nextLine();
					System.out.println("State of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;
				case 4:
					System.out.println("Enter New Zip of "+contactArrayList.get(i).firstLastName);
					contactArrayList.get(i).zip=scanner.nextLong();
					System.out.println("Zip of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;
				case 5:
					System.out.println("Enter New Phone Number of "+contactArrayList.get(i).firstLastName);
					contactArrayList.get(i).phoneNum=scanner.nextLong();
					System.out.println("Phone Number of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;
				case 6:
					System.out.println("Enter New Email of "+contactArrayList.get(i).firstLastName);
					scanner.nextLine();
					contactArrayList.get(i).email=scanner.nextLine();
					System.out.println("Email of "+contactArrayList.get(i).firstLastName+" is updated Successfully");
					break;
				default:
					System.out.println("You have Entered Wrong Choice.Please enter choice between 1 to 6 ");
			}
		}
	}
	public void deleteContact() {
		int i=searchContact();
		if(i>=0) {
			contactArrayList.remove(i);
			System.out.println("Contact Details are Deleted");
		}
	}
	public void performContactOperation(MultipleContacts contact) {
		int choice;
		int flag=0;
		while(flag==0) {
			choice=contact.displayMenu();
			switch(choice) {
				case 1:
					contact.addContact();
					break;
				case 2:
					contact.editContact();
					break;
				case 3:
					contact.showContact();
					break;
				case 4:
					contact.showAllContacts();
				case 5:
					contact.deleteContact();
					break;
				case 6:
					flag=1;
					break;
				default:
					System.out.println("You have Entered Wrong Choice.Please enter option between 1 to 6");
			}
		}
	}
	public static void main(String[] args) {
		MultipleContacts ob=new MultipleContacts();
		ob.addContact();
		ob.addContact();
		ob.addContact();
	}
}
