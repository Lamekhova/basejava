package com.urise.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapUuidStorageTest.class
        })
public class SuiteTestClass {
}
