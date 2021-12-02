package com.bridgelabz.junit;
//Uc18
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import com.bridgelabz.addressbook.AddressBookDb;
import com.bridgelabz.addressbook.Contacts;
public class AddressBookDbTest {
	@Test
	public void givenAddressBookDb_whenRetrived_shouldMatchRecordCount() {
		AddressBookDb addressBookDb=new AddressBookDb();
		List<Contacts> addressBookList=addressBookDb.readRecordsFromDatabase();
		assertEquals(2,addressBookList.size());
	}
	@Test
	public void givenNewPhoneNumForPerson_whenUpdated_shouldSyncWithDb() {
		AddressBookDb addressBookDb=new AddressBookDb();
		int result=addressBookDb.updatePhoneNum("Gayo","7453628960");
		assertEquals(1,result);
	}
	@Test
	public void givenDateRange_whenRetrived_shouldMatchPersonCount() {
		AddressBookDb addressBookDb=new AddressBookDb();
		LocalDate startDate=LocalDate.of(2021,01,01);
		LocalDate endDate=LocalDate.now();
		int result=addressBookDb.getAddressBookDataWithinDateRange(startDate,endDate).size();
		assertEquals(1,result);
	}
}
