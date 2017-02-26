package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import static android.R.attr.max;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private int cupOfCoffe = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        float price = calculatePrice();
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_box);
        boolean hasChocolate = checkBox2.isChecked();
        String message = createOrderSummary(price,hasWhippedCream,hasChocolate);
        displayMessage(message);
    }

    /**
     * Creates the summary of the order for the currunt purchase
     */
    public String createOrderSummary(float price, boolean addWhippedCream, boolean addChocolate){
        String summary = "Name: Ennio Nasca"
                +"\nadd whipped cream? " + addWhippedCream
                +"\nadd chocolate? " + addChocolate
                +"\nQuantity: " + quantity
                +"\nTotal: €" + price
                +"\nThank You!";
        return summary;
    }

    /**
     * Calculates the price of the order.
     *
     */
    private float calculatePrice() {
        float price = quantity * cupOfCoffe;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view){
        quantity = quantity +1;
        display(quantity);
    }

    public void decrement(View view){
        quantity = quantity - 1;
        if (quantity<0)
            quantity = 0;
        display(quantity);
    }
}