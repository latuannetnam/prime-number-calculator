package com.netnam.prime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

/**
 * Created by latuan on 15/02/2015.
 */
public class Main {
   private final Logger logger = LogManager.getLogger(Main.class);
    @Option(name="-f",usage="first number")
    private long firstNumber = 1;
    @Option(name="-l",usage="last number")
    private long lastNumber=1000;
    @Option(name="-i",usage="master host IP address")
    private String hostname="127.0.0.1";
    @Option(name="-p",usage="master port address")
    private Integer port=3551;
    @Option(name="-m",usage="Run mode: s (single thread); t (multithread); m (master node); w (worker node)")
    private String mode="s";
    @Option(name="-s",usage="Segment size")
    private long segmentNumber=1000000;

    @Option(name="-h", usage = "Help")
    private boolean help;
    public static void main(String[] args) {
        new Main().doMain(args);

    }

    private void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(100));

        try {
            // parse the arguments.
            parser.parseArgument(args);
            if (args.length==0)
            {
                System.out.print("Prime number calculator. Usage:\n");
                parser.printUsage(System.err);
                System.err.println();

            }

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            logger.error(e.getMessage());
            logger.info("Main [options...]");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        if (help)
        {
            System.out.print("Prime number calculator. Usage:\n");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        if (mode.equals("s"))
        {
            new SingleThread(firstNumber,lastNumber);
        }
        else if (mode.equals("t"))
        {
            new SingleNode(firstNumber,lastNumber);
        }
        else if (mode.equals("m"))
        {
            new MasterNode(firstNumber,lastNumber,hostname,port,segmentNumber);
        }
        else if (mode.equals("w"))
        {
            new WorkerNode(hostname,port);
        }
        else
        {
            logger.error("Wrong mode!");
            return;
        }




    }
}
