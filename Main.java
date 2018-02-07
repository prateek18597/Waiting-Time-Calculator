import java.io.File;
import java.util.Scanner;

public class Main
{
	int customer=0;
	double time=0;
	double startTime=0;
	double currentTime=0;
	ShopNode[] shop;
	CostumerNode[] cust;
	int index1=0;
	int index2=0;


	void Heapify1(int pos,int cond)
	{

		if(cond==1)
		{
			
			while(true)
			{
				if(2*pos+1<index1 && 2*pos+2<index1)
				{
					int min=shop[2*pos+1].count<shop[2*pos+2].count?2*pos+1:2*pos+2;
					if(shop[min].count<shop[pos].count)
					{
						ShopNode temp;
						temp=shop[pos];
						shop[pos]=shop[min];
						shop[min]=temp;
					}
					else
					{
						break;
					}
				}
				else
				{
					if(2*pos+1<index1)
					{
						int min=2*pos+1;
						if(shop[min].count<shop[pos].count)
						{
							ShopNode temp;
							temp=shop[pos];
							shop[pos]=shop[min];
							shop[min]=temp;
						}
						else
						{
							break;
						}		
					}
					else
					{
						break;
					}	
				}
			}
		}
		else
		{
			while(pos>=1)
			{
				if(shop[pos].count<shop[pos/2].count)
				{
					ShopNode temp;
					temp=shop[pos];
					shop[pos]=shop[pos/2];
					shop[pos/2]=temp;
				}
				else
				{
					break;
				}
			}	
		}
	}


	void Heapify2(int pos,int cond)
	{
		if(cond==1)
		{
			while(pos>=1)
			{
				if(cust[pos].departure<cust[pos/2].departure)
				{
					CostumerNode temp;
					temp=cust[pos];
					cust[pos]=cust[pos/2];
					cust[pos/2]=temp;
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			while(true)
			{
				if(2*pos+1<=index2 && 2*pos+2<=index2)
				{
					int min=cust[2*pos+1].departure<cust[2*pos+2].departure?2*pos+1:2*pos+2;
					if(cust[min].departure<cust[pos].departure)
					{
						CostumerNode temp;
						temp=cust[pos];
						cust[pos]=cust[min];
						cust[min]=temp;
					}
					else
					{
						break;
					}
				}
				else
				{
					if(2*pos+1<=index2)
					{
						int min=2*pos+1;
						if(cust[min].departure<cust[pos].departure)
						{
							CostumerNode temp;
							temp=cust[pos];
							cust[pos]=cust[min];
							cust[min]=temp;
						}		
					}
					else
					{
						break;
					}	
				}
			}
		}
	}

	void DeleteMin()
	{

		int queue_no=cust[0].que_no;
		//System.out.println(customer+"St");
		for(int i=0;i<index1;i++)
		{
			if(shop[i].que==queue_no)
			{
				shop[i].count--;
				shop[i].time-=cust[0].serviceTime;
				if(index2!=0)
					Heapify1(i,0);
				break;
			}
		}
		customer++;
		//System.out.println(customer);
		time+=cust[0].departure-cust[0].arrival-cust[0].serviceTime;
		//System.out.println("Done " +index2);
		
		CostumerNode temp=cust[index2];
		//System.out.println("Done " +index2);
		
		cust[0]=temp;
		//System.out.println("Done " +index2);
		
		index2--;
		
		if(index2!=1)
			Heapify2(0,0);
	}

	void AverageTime()
	{
		int result=(int)(time*100)/customer;
		Double r=result/100.0;
		System.out.println(r);
	}

	public static void main(String args[])
	{
		try{
		Main m=new Main();
		//File file=new File("input.txt");
		Scanner s=new Scanner(System.in);
		Double rate=Double.parseDouble(s.next());
		Double mean=Double.parseDouble(s.next());
		Double variance=Double.parseDouble(s.next());
		int k=s.nextInt();
		m.index1=k;
		Double invRate=1/rate;
		int num=s.nextInt();

		m.shop=new ShopNode[k];
		m.cust=new CostumerNode[num];
		
		for(int i=0;i<k;i++)
		{
			m.shop[i]=new ShopNode(i);

		}

		for(int i=0;i<num;i++)
		{
			m.cust[i]=new CostumerNode();
		}

		for(int i=0;i<num;i++)
		{
			if(m.index2>0)
			{
				//System.out.println("Bye");
				while(m.currentTime>m.cust[0].departure)
				{
					//make a delete function for heap2
					m.DeleteMin();

				}
			}
			double x=Math.random();
			m.currentTime+=x*invRate;
			m.currentTime+=(invRate-x*invRate);
			m.cust[m.index2].arrival=m.currentTime;
			m.cust[m.index2].serviceTime=(mean-variance)+Math.random()*(2*variance);
			int y=(int)(m.cust[m.index2].serviceTime*100);
			x=y/100.0;
			System.out.print(x+" ");
			m.shop[0].count++;
			m.cust[m.index2].que_no=m.shop[0].que;
			m.shop[0].time+=m.cust[m.index2].serviceTime;
			m.cust[m.index2].departure=m.cust[m.index2].arrival+m.shop[0].time;
			
			m.Heapify2(m.index2,1);
			m.index2++;
			//System.out.println("Problem Detected");
			
			m.Heapify1(0,1);

			

		}
		m.index2--;
		System.out.println();
		while(m.index2>0)
		{
			m.DeleteMin();

		}
		//System.out.println("AverageTime");
		m.AverageTime();

	}
	catch(Exception e)
	{
		System.out.println("Eror"+e.getMessage());
	}



	}

}
