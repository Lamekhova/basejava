import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    // Обнуляем массив
    void clear() {
        if (size > 0) {
            Arrays.fill(storage, 0, size, null);
        }
        size = 0;
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
        for (int i = 0; i < size-1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, storage.length - i - 1);
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }
}
