package IO;

public class ServicePricing {
    public static Object[][] getServiceDetails(String serviceType) {

        if (serviceType.equals("Normal Service")) {
            return new Object[][] {
                    {"Fully Synthetic Engine Oil (4L)", 150.00},
                    {"Oil Filter Replacement", 35.00},
                    {"Drain Plug Washer", 5.00},
                    {"Tire Rotation & Balancing", 45.00},
                    {"Multipoint Inspection", 0.00}
            };

        } else if (serviceType.equals("Major Service")) {
            return new Object[][] {
                    {"Fully Synthetic Engine Oil (4L)", 150.00},
                    {"Oil Filter Replacement", 35.00},
                    {"Drain Plug Washer", 5.00},
                    {"Spark Plugs Replacement (Set of 4)", 120.00},
                    {"Transmission Fluid Flush", 180.00},
                    {"Brake Fluid Replacement", 80.00},
                    {"Cabin Air Filter (A/C)", 65.00},
                    {"Multipoint Inspection", 0.00}
            };

        } else {
            return new Object[][] {
                    {"Custom / Unknown Service", 0.00}
            };
        }
    }

    public static String getTotalPriceFormatted(String serviceType) {
        Object[][] details = getServiceDetails(serviceType);
        double total = 0.0;

        for (int i = 0; i < details.length; i++) {
            double price = (Double) details[i][1];
            total += price;
        }

        return String.format("RM %.2f", total);
    }
}