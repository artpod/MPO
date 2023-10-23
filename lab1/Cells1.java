public class Cells1 {
    private static int[] cells; // Масив клітин, в яких зберігаються атоми

    // Клас, який представляє потік для кожного атома
    class AtomThread implements Runnable {
        private int position; // Поточна позиція атома

        public AtomThread(int startPos) {
            this.position = startPos; // Ініціалізація початкової позиції атома
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (cells) { // "правило", яке каже, що лише одна людина може рухатися відразу
                    double m = Math.random(); // Рухаємо атом вліво чи вправо в залежності від випадкового числа
                    if (m > p) {
                        if (position < N - 1) {
                            cells[position]--; // Зменшуємо кількість атомів в поточній клітинці
                            cells[position + 1]++; // Збільшуємо кількість атомів в наступній клітинці
                            position++; // Оновлюємо позицію атома
                        }
                    } else {
                        if (position > 0) {
                            cells[position]--; // Зменшуємо кількість атомів в поточній клітинці
                            cells[position - 1]++; // Збільшуємо кількість атомів в попередній клітинці
                            position--; // Оновлюємо позицію атома
                        }
                    }
                }
                try {
                    Thread.sleep(100);  // додано, щоб уповільнити виконання
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Встановлюємо флаг переривання, якщо потік було перервано
                }
            }
        }
    }

    public static int getCell(int i) {
        synchronized (cells) { // Синхронізація доступу до масиву клітин
            return cells[i]; // Повертаємо кількість атомів в вказаній клітинці
        }
    }

    private static int N; // Кількість клітин
    private static int K; // Загальна кількість атомів
    private static double p; // Ймовірність руху атома вправо

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            System.out.println("Використання: Cells1 N K p");
            return;
        }

        N = Integer.parseInt(args[0]);
        K = Integer.parseInt(args[1]);
        p = Double.parseDouble(args[2]);

        cells = new int[N];
        cells[0] = K; // Спочатку всі атоми знаходяться в першій клітинці

        Thread[] threads = new Thread[K];
        for (int i = 0; i < K; i++) {
            threads[i] = new Thread(new Cells1().new AtomThread(0)); // Створюємо поток для кожного атома
            threads[i].start(); // Запускаємо поток
        }

        Thread.sleep(10000);  // 10 секунд
        for (int i = 0; i < K; i++) {
                System.out.print(cells[i]+" ");
            }

        for (Thread thread : threads) {
           /*  for (int i = 0; i < K; i++) {
                System.out.print(cells[i]+" ");
            }*/
            //System.out.println();
            thread.interrupt(); // Перериваємо роботу потоків-атомів
        }

        int sum = 0;
        for (int cell : cells) {
            sum += cell; // Рахуємо загальну кількість атомів у всіх клітинках
        }
        System.out.println("Загальна кількість атомів: " + sum);
    }
}
