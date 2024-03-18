package proyechrrn;
public class Proceso {
    //ATRIBUTOS
    private String NP;      private int HLL;        private int TC;
    private int HS=0;       private int TS;         private int TE;
    private double IS;      private int pos;
    //SET Y GET
    String getNP()
    {   return this.NP;
    }
    int getHLL()
    {   return this.HLL;
    }
    int getTC()
    {   return this.TC;
    }
    int getHS()
    {   return this.HS;
    }
    int getTS()
    {   return this.TS;
    }
    int getTE()
    {   return this.TE;
    }
    double getIS()
    {   return this.IS;
    }
    void setHS(int hs)
    {   this.HS = hs;
        this.TS = this.HS - this.HLL;
        this.TE = this.TS - this.TC;
        this. IS = (double)this.TC/this.TS;
    }
    int getPOS()
    {   return this.pos;
    }
    // CON HORA DE LLEGADA
    Proceso(String n,int h,int t,int pos)
    {   this.NP = n;
        this.HLL = h;
        this.TC = t;
        this.pos = pos;
    }
    //SIN HORA DE LLEGADA
    Proceso(String n,int t,int pos)
    {   this.NP = n;
        this.HLL = 0;
        this.TC = t;
        this.pos = pos;
    }

    
}
