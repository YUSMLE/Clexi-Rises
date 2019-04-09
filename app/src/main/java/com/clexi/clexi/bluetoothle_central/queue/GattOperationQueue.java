package com.clexi.clexi.bluetoothle_central.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yousef on 8/10/2016.
 */

public class GattOperationQueue
{

    public static final String TAG = GattOperationQueue.class.getSimpleName();

    // The queue of pending transmissions
    private Queue<GattOperation> queue = new LinkedList<GattOperation>();

    private boolean isQueueProcessing = false;

    /**
     * Empty Constructor
     */
    public GattOperationQueue()
    {
        // Nothing
    }

    /**
     * Add a transaction item to transaction queue
     *
     * @param gattOperation
     */
    public GattOperation addToQueue(GattOperation gattOperation)
    {
        queue.add(gattOperation);

        // If there is no other transmission processing, go do this one!
        if (!isQueueProcessing)
        {
            return getFromQueue();
        }
        else
        {
            return null;
        }
    }

    /**
     * Call when a transaction has been completed.
     * Will process next transaction if queued.
     */
    public GattOperation getFromQueue()
    {
        if (queue.size() <= 0)
        {
            isQueueProcessing = false;

            return null;
        }

        isQueueProcessing = true;

        return queue.remove();
    }

    public void resetQueue()
    {
        queue.clear();
        isQueueProcessing = false;
    }
}
