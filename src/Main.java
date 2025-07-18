import vista.VistaLoader;

public class Main {
    public static void main(String[] args) {
        debugGeneralInformation();
        VistaLoader vistaLoader = new VistaLoader();
        vistaLoader.printMenu();
    }

    private static void debugGeneralInformation() {
        System.out.println("Gestor de Amistades. Desarrollado por NV, AB y DM");
        System.out.println("Alpha-version");


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
        for (int i = 0; i < 2; i++) {
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

        // Mensaje final
        System.out.print("\rPrograma cargado con exito         ");
        System.out.flush();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Borra la línea para limpiar la consola
        System.out.print("\r                              \r");
        System.out.flush();
    }



}
