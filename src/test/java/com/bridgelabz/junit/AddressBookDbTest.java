package com.bridgelabz.junit;
//Refactor Uc16
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import com.bridgelabz.addressbook.AddressBookDb;
import com.bridgelabz.addressbook.Contacts;
public class AddressBookDbTest {
	@Test
	public void givenAddressBookDb_whenRetrived_shouldMatchRecordCount() {
		AddressBookDb addressBookDb=new AddressBookDb();
		List<Contacts> addressBookList=addressBookDb.readRecordsFromDatabase();
		assertEquals(1,addressBookList.size());
	}
}
