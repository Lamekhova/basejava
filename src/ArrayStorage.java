import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[1000];
    int size = 0;

    // Обнуляем массив
    void clear() {
            Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // Находим первую ячейку == null, в нее добавляем резюме, выходим из цикла
    void save(Resume r) {
        if (size < storage.length) {
            storage[size] = r;
            size++;
        }

    }

    // Возвращаем резюме если найдено, иначе возвращаем null
    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    // Удаляем резюме из storage
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, storage.length - i - 1);
                size--;
                break;
            }
        }
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
