package phonebook;

public class JumpingSearch {

    public static void mainMethod() {

        Integer[] filler = new Integer[102];
        for (int i = 0, j = 1; i < 102; i++, j++) {
            filler[i] = j;
        }
//        Integer[] array = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233};
        Integer[] elementsToSearch = {1, 11, 10, 20, 2, 12, 32};
        for (Integer next : elementsToSearch) {
            int arrIndex = jumpSearch(filler, next);
            System.out.println(arrIndex);
        }
    }


    public static int jumpSearch(Integer[] arr, int x) {
        if (arr.length == 0) {
            return -1;
        }
        int current = 0;
        int previous = 0;
        int last = arr.length;
        int step = (int) Math.floor(Math.sqrt(last));

        while (arr[current] < x) {
            if (current == last) {
                return -1;
            }
            previous = current;
            current = Math.min(current + step, last);

        }

        while (arr[current] >= x) {
            if (arr[current] == x) {
                return current;
            }

            current = current - 1;
            if (current <= previous) {
                return -1;
            }
        }
        return -1;
    }

    public static int jumpSearchDesc(Integer[] array, int target) {
        int n = array.length;

        int step = (int) Math.floor(Math.sqrt(n));

        int currentIndex = 0;// index of current element, starts from 0
        int helper = 0;

        while (currentIndex < n) {
            if (array[currentIndex] == target) {
                return currentIndex;
            } else if (array[currentIndex] < target) {// maybe this block contains target element
                // backward linear search
                helper = currentIndex;// the index of block end
                int blockPrevIndex = helper - step + 1;// the index of block start

                while (helper >= blockPrevIndex && helper >= 0) {
                    if (array[helper] == target) {
                        return helper;
                    }
                    helper--;
                }
                return -1;// dont search target in this block, it's make no sense to search other blocks
            }

            // dont meet above cases, move to next block
            currentIndex += step;
        }

        // target element in last block, the size of last block < step
        helper = n - 1;// index of last element
        int lastBlockPrevIndex = currentIndex - step + 1;
        while (helper >= lastBlockPrevIndex) {
            if (array[helper] == target) {
                return helper;
            }
            helper--;
        }
        // dont search target in last block
        return -1;
    }
}
