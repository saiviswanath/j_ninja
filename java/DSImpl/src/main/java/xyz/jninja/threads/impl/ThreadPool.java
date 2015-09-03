/**
 * 
 */
package xyz.jninja.threads.impl;

import java.util.ArrayList;
import java.util.List;

import xyz.jninja.ds.impl.BlockingQueue;

/**
 * @author viswa
 *
 */
public class ThreadPool {
  private BlockingQueue bQueue;
  private List<PooledThread> idleThreadPool;
  private boolean isStopped = false;

  public ThreadPool(int maxThreads, int maxTasks) {
    bQueue = new BlockingQueue(maxTasks);
    idleThreadPool = new ArrayList<PooledThread>();

    for (int i = 0; i < maxThreads; i++) {
      idleThreadPool.add(new PooledThread(bQueue));
    }

    for (PooledThread pooledThread : idleThreadPool) {
      pooledThread.start();
    }
  }

  public void execute(Runnable runnable) throws InterruptedException {
    if (this.isStopped) {
      throw new RuntimeException("Thread Pool Stopped");
    }
    this.bQueue.enqueue(runnable);
  }

  public void stop() {
    this.isStopped = true;
    for (PooledThread pooledThread : idleThreadPool) {
      pooledThread.doStop();
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    ThreadPool pool = new ThreadPool(5, 5);
    for (int i = 0; i < 5; i++) {
      final int tmp = i;
      try {
        pool.execute(new Runnable() {

          @Override
          public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + tmp);
          }
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    pool.stop();
  }
}
