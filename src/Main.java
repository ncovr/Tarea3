import vista.VistaLoader;

public class Main {
    public static void main(String[] args) {
        debugGeneralInformation();
        VistaLoader vistaLoader = new VistaLoader();
        vistaLoader.printMenu();
    }

    private static void debugGeneralInformation() {
        System.out.println("Pre-alpha version");

        String[] estados = {
                "[                    ]",
                "[=                   ]",
                "[==                  ]",
                "[===                 ]",
                "[====                ]",
                "[=====               ]",
                "[======              ]",
                "[=======             ]",
                "[========            ]",
                "[=========           ]",
                "[==========          ]",
                "[===========         ]",
                "[============        ]",
                "[=============       ]",
                "[==============      ]",
                "[===============     ]",
                "[================    ]",
                "[=================   ]",
                "[==================  ]",
                "[=================== ]",
                "[====================]"
        };

        System.out.print("Cargando programa ");
        for (int i = 0; i < 2; i++) { // repite 3 veces la animación
            for (String estado : estados) {
                System.out.print("\rCargando programa " + estado);
                System.out.flush();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println();
    }


}
