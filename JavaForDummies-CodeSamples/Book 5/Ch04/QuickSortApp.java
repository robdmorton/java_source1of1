public class QuickSortApp
{
	public static void main(String[] args)
	{
		int LEN = 100;
		int[] unsorted = new int[LEN];
		for (int i = 0; i<LEN; i++)
			unsorted[i] = (int)(Math.random() * 100) + 1;
		System.out.println("Unsorted array:");
		printArray(unsorted);
		int[] sorted = sort(unsorted);
		System.out.println("\n\nSorted array:");
		printArray(sorted);
	}

	private static void printArray(int[] array)
	{
		System.out.println();
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] < 10)
				System.out.print(" ");
			System.out.print(array[i] + " ");
			if ((i+1) % 20 == 0)
				System.out.println();
		}
	}

	private static int[] a;

	public static int[] sort(int[] array)
	{
		a = array;
		sort(0, a.length - 1);       // sort the entire array
		return a;
	}

	public static void sort(int low, int high)
	{
		if (low >= high)
			return;
		int p = partition(low, high);
		sort (low, p);
		sort (p+1, high);
	}

	public static int partition(int low, int high)
	{
		int pivot = a[low];

		int i = low - 1;
		int j = high + 1;

		while (i < j)
		{
			for (i++; a[i] < pivot; i++);
			for (j--; a[j] > pivot; j--);
			if (i < j)
				swap(i, j);
		}
		return j;
	}

	public static void swap(int i, int j)
	{
		//int temp = a[i];
		//a[i] = a[j];
		//a[j] = temp;
		a[i] ^= a[j];
		a[j] ^= a[i];
		a[i] ^= a[j];
	}
}