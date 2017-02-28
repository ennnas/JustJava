package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        //Looks for which topping is selected
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_box);
        boolean hasChocolate = checkBox2.isChecked();
        // calculate the price for the actual order
        float price = calculatePrice(hasChocolate,hasWhippedCream);
        // gets the name of the person ordering
        EditText editText = (EditText) findViewById(R.id.customer_name);
        String customerName = editText.getText().toString();
        // generate the order summary for the email text
        String message = createOrderSummary(price, customerName, hasWhippedCream, hasChocolate);
        // creates an Intent for the mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"JustJavaApp@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for "+customerName);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }

    /**
     * Creates the summary of the order for the currunt purchase
     */
    public String createOrderSummary(float price, String customerName, boolean addWhippedCream, boolean addChocolate){
        String summary = "Name: " + customerName
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
    private float calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        float price = cupOfCoffe;
        if(hasChocolate)
            price += 2;
        if(hasWhippedCream)
            price += 1;
        price *= quantity ;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view){
        quantity = quantity +1;
        if (quantity>100) {
            quantity = 100;
            Toast.makeText(this,"You can not order more then 100 coffees",Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view){
        quantity = quantity - 1;
        if (quantity<1) {
            quantity = 1;
            Toast.makeText(this, "You can not order less then 1 coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }
}