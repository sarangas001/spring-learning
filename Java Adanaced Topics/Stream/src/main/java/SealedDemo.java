sealed class Alpha permits Beta
{

}

//final class Beta extends  Alpha
//sealed class Beta extends  Alpha permits Gamma
non-sealed class Beta extends  Alpha
{

}

class Gamma
{

}

public class SealedDemo {
    public static void main(String[] args) {

    }
}
