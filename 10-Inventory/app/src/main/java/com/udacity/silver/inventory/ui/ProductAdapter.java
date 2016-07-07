package com.udacity.silver.inventory.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final InventoryDbHelper dbHelper;
    private final Context context;
    private final DecimalFormat dollarFormat;
    private final View emptyView;
    private List<Product> productList;

    ProductAdapter(Context context, View emptyView) {
        this.context = context;
        this.emptyView = emptyView;
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dbHelper = new InventoryDbHelper(context);
        refresh();
    }

    void refresh() {
        productList = dbHelper.getAllProducts();

        if (productList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

        notifyDataSetChanged();
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        return new ProductViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.name.setText(product.name);
        holder.price.setText(dollarFormat.format((float) product.priceInCents / 100));
        holder.quantity.setText(context.getString(R.string.quantity, product.quantity));


        if (product.quantity < 1) {
            holder.sellButton.setText(R.string.order);
            holder.sellButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.sendEmailToSupplier(context);
                }
            });
        } else {


            holder.sellButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.quantity -= 1;
                    dbHelper.changeQuantity(product.name, -1);
                    onBindViewHolder(holder, holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView price;
        TextView quantity;
        Button sellButton;

        ProductViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            sellButton = (Button) itemView.findViewById(R.id.sell_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.PRODUCT_KEY, productList.get(getAdapterPosition()));
            context.startActivity(intent);

        }
    }
}
