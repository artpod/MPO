public class Cells0 {
    private static int[] cells;

    // Клас, який представляє потік для кожного атома
    class AtomThread implements Runnable {
        private int position;

        public AtomThread(int startPos) {
            this.position = startPos; // початкова позиція атома
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                double m = Math.random(); // Рухаємо атом вліво чи вправо в залежності від випадкового числа
                if (m > p) {
                    if (position < N - 1) {
                        cells[position]--; // зменшуємо кількість атомів в поточній клітинці
                        cells[position + 1]++; // збільшуємо кількість атомів в сусідній клітинці
                        position++; // оновлюємо позицію атома
                    }
                } else {
                    if (position > 0) {
                        cells[position]--;
                        cells[position - 1]++;
                        position--;
                    }
                }
                try {
                    Thread.sleep(100);  // додано, щоб уповільнити виконання
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // якщо потік було перервано, встановлюємо флаг переривання
                }
            }
        }
    }

    public static int getCell(int i) {
        return cells[i]; // отримуємо кількість атомів у вказаній клітинці
    }

    private static int N; // кількість клітин
    private static int K; // кількість атомів
    private static double p; // ймовірність руху атома вправо

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            System.out.println("Використання: Cells0 N K p");
            return;
        }

        N = Integer.parseInt(args[0]);
        K = Integer.parseInt(args[1]);
        p = Double.parseDouble(args[2]);

        cells = new int[N];
        cells[0] = K; // спочатку всі атоми знаходяться в клітинці 0

        Thread[] threads = new Thread[K];
        for (int i = 0; i < K; i++) {
            threads[i] = new Thread(new Cells0().new AtomThread(0)); // створюємо поток для кожного атома
            threads[i].start(); // запускаємо потік
        }

        Thread.sleep(10000);  // 10 секунд

        for (Thread thread : threads) {
            thread.interrupt(); // перериваємо всі потоки-атоми
        }

        int sum = 0;
        for (int cell : cells) {
            sum += cell; // рахуємо загальну кількість атомів
        }
        System.out.println("Загальна кількість атомів: " + sum);
    }
}
