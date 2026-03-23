package console;

import model.HumanBeing;

/**
 * Интерфейс для чтения объектов HumanBeing.
 * Позволяет абстрагировать источник ввода (консоль, файл, тесты).
 * Реализует принцип Dependency Inversion (SOLID).
 */
public interface InputReader {

    /**
     * Читает объект HumanBeing из источника ввода.
     * Реализация должна обеспечивать пошаговый ввод всех полей
     * с валидацией и возможностью повторного ввода при ошибке.
     *
     * @return созданный объект HumanBeing, или null в случае критической ошибки
     */
    HumanBeing readHumanBeing();
}
