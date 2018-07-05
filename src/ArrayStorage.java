import java.util.ArrayList;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    // Обнуляем массив
    void clear() {
        if (size > 1) {
            Arrays.fill(storage, 0, size-1, null);
        }
    }

    // Находим первую ячейку == null, в нее добавляем резюме, выходим из цикла
    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    // Возвращаем резюме если найдено, иначе возвращаем null
    Resume get(String uuid) {
        Resume findResume = null;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                findResume = storage[i];
                break;
            }
        }
        return findResume;
    }

    // Удаляем резюме из storage
    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid == uuid) {
                for (int j = i; j < storage.length - 1; j++) {
                    if (storage[j + 1] != null) {
                        storage[j] = storage[j+1];
                    } else {
                        storage[j] = null;
                        break;
                    }
                }
                break;
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    // Не нашла я метод в Arrays
    Resume[] getAll() {
        ArrayList<Resume> tempList = new ArrayList();
        for (Resume element : storage) {
            if (element != null) {
                tempList.add(element);
            } else {
                break;
            }
        }
        return tempList.toArray(new Resume[0]);
    }

    int size() {
        return size;
    }
}
