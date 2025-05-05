import mpi.*;

public class DistributedSum {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] array = null;
        int n = 10;  // Total elements

        if (rank == 0) {
            array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = i + 1; // Filling array [1, 2, ..., 10]
            }
        }

        // Determine how many elements per process
        int localN = n / size;
        int remainder = n % size;

        // Root creates custom counts and displacements
        int[] sendCounts = new int[size];
        int[] displs = new int[size];
        for (int i = 0; i < size; i++) {
            sendCounts[i] = localN + (i < remainder ? 1 : 0);
            displs[i] = (i == 0) ? 0 : displs[i - 1] + sendCounts[i - 1];
        }

        int[] localArray = new int[sendCounts[rank]];
        MPI.COMM_WORLD.Scatterv(array, 0, sendCounts, displs, MPI.INT, localArray, 0, sendCounts[rank], MPI.INT, 0);

        // Compute local sum
        int localSum = 0;
        for (int i : localArray) localSum += i;

        // Reduce to get total sum
        int[] globalSum = new int[1];
        MPI.COMM_WORLD.Reduce(new int[]{localSum}, 0, globalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        System.out.println("Processor " + rank + " calculated local sum = " + localSum);

        if (rank == 0) {
            System.out.println("Total sum = " + globalSum[0]);
        }

        MPI.Finalize();
    }
}

