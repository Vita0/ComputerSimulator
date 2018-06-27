package computersimulator.CPU;

class ALU {
    static int sum(int regA, int regB){
        return (regA+regB);
    }
    static boolean isAPossitive(int regA){
        return (regA>0);
    }
    static int inv(int reg){
        return -reg;
    }
    static int mul(int regA, int regB){
        return (regA*regB);
    }
    static int div(int regA, int regB){
        //try{
        if (regB == 0) {
            throw new DivisionByZeroALUException();
        }
        //} catch (DivisionByZeroALUException e){
        //    return regA;
        //}
        return (regA/regB);
    }
}
