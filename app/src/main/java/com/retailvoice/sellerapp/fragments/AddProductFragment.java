package com.retailvoice.sellerapp.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ImageUtil;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.models.Category;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {
    private static final String TAG = "AddProductFragment";

    private FloatingActionButton done;
    private Spinner categories;
    View view;
    ArrayList<Category> categoriesList;
    ArrayList<String> spinnerList;
    Spinner spinnerCategories;
    ImageView productImage;
    Uri photoUri;

    private Bitmap mImageToBeAttached;
    private String mImagePathToBeAttached;

    public final static int PICK_PHOTO_CODE = 1046;

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CHOOSE_PHOTO = 2;
    private static final int THUMBNAIL_SIZE = 150;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_product, parent, false);

        final Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        DataManager manager = DataManager.getSharedInstance(getContext());

        QueryEnumerator categories = null;
        try {
            categories = Category.getCategories(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        final List categoriesList = new ArrayList<>();
        for (QueryRow category : categories) {
            Document document = category.getDocument();
            Category model = ModelHelper.modelForDocument(document, Category.class);
            categoriesList.add(model);
        }

        spinnerList = new ArrayList<>();

        for(int i=0; i<categoriesList.size(); i++) {
            Category category = (Category) categoriesList.get(i);
            spinnerList.add(category.getName().toString());
        }


        spinnerCategories = (Spinner) view.findViewById(R.id.spinnerCategory);

        spinnerCategories
                .setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        spinnerList));

        done = (FloatingActionButton) view.findViewById(R.id.btnDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editName = (EditText) view.findViewById(R.id.etName);
                final EditText editPrice = (EditText) view.findViewById(R.id.etPrice);
                final EditText editInventory = (EditText) view.findViewById(R.id.etInventory);

                Product product = new Product();
                product.setName(editName.getText().toString());
                String price = editPrice.getText().toString();
                product.setPrice(Integer.parseInt(price));
                String inventory = editInventory.getText().toString();
                product.setInventory(Integer.parseInt(inventory));
//                product.setImageUri(imageUri.toString());

                int position = spinnerCategories.getSelectedItemPosition();
                Category category = (Category) categoriesList.get(position);
                product.setCategoryId(category.get_id());

                product.setType("product");

                if (editName.getText() == null || editPrice.getText().toString().equals("") || editInventory.getText().toString().equals(" ")) {
                    Toast.makeText(getActivity(), "Entry not saved, missing title", Toast.LENGTH_SHORT).show();
                } else {
                    ModelHelper.save(DataManager.getSharedInstance(getContext()).database, product, mImageToBeAttached);

                    Toast.makeText(getActivity(), "Entry saved", Toast.LENGTH_SHORT).show();
                }

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new AllProductsFragment());
                ft.commit();
            }
        });

        productImage = (ImageView) view.findViewById(R.id.ivProduct);
        productImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAttachImageDialog();
            }
        });
        return view;
    }

    private void displayAttachImageDialog() {
        CharSequence[] items;
        if (mImageToBeAttached != null)
            items = new CharSequence[] { "Take photo", "Choose photo", "Delete photo" };
        else
            items = new CharSequence[] { "Take photo", "Choose photo" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    dispatchTakePhotoIntent();
                } else if (item == 1) {
                    dispatchChoosePhotoIntent();
                } else {
                    deleteCurrentPhoto();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e(TAG, "Cannot create a temp image file", e);
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void deleteCurrentPhoto() {
        if (mImageToBeAttached != null) {
            mImageToBeAttached.recycle();
            mImageToBeAttached = null;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.ivProduct);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
    }

    private void dispatchChoosePhotoIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CHOOSE_PHOTO);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "TODO_LITE-" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(fileName, ".jpg", storageDir);
        mImagePathToBeAttached = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        final int size = THUMBNAIL_SIZE;
        Bitmap thumbnail = null;
        if (requestCode == REQUEST_TAKE_PHOTO) {
            File file = new File(mImagePathToBeAttached);
            if (file.exists()) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mImagePathToBeAttached, options);
                options.inJustDecodeBounds = false;
                mImageToBeAttached = BitmapFactory.decodeFile(mImagePathToBeAttached, options);
                    thumbnail = ThumbnailUtils.extractThumbnail(mImageToBeAttached, size, size);

                // Delete the temporary image file
                file.delete();
            }
            mImagePathToBeAttached = null;
        } else if (requestCode == REQUEST_CHOOSE_PHOTO) {
            try {
                Uri uri = data.getData();
                ContentResolver resolver = getActivity().getContentResolver();
                mImageToBeAttached = MediaStore.Images.Media.getBitmap(resolver, uri);
                    AssetFileDescriptor asset = resolver.openAssetFileDescriptor(uri, "r");
                    thumbnail = ImageUtil.thumbnailFromDescriptor(asset.getFileDescriptor(), size, size);
            } catch (IOException e) {
                Log.e(TAG, "Cannot get a selected photo from the gallery.", e);
            }
        }

        if (thumbnail != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.ivProduct);
            imageView.setImageBitmap(thumbnail);
        }
    }

}