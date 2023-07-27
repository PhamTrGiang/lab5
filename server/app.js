const express = require('express');
const mongoose = require('mongoose');

const ProductModel = require('./model/Products');
const uri = "mongodb://127.0.0.1:27017/bailab";
const bodyParser = require('body-parser');


mongoose.connect(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => {
    console.log('Da ket noi voi MongoDB');
  })
  .catch((err) => {
    console.error('Khong ket noi dc MongoDB: ', err);
  })

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.get('/listProduct', async function (req, res) {
  try {
    const productList = await ProductModel.find().lean();
    res.status(200).json(productList);
  } catch (err) {
    res.status(500).json({"success": 0,"message": "No products found"});
  }
})


// Định nghĩa API thêm sản phẩm
app.post('/addProduct', async (req, res) => {
  try {
    const { name, price , description} = req.body;
    const time = new Date;
    const create_at = time.getDate()+'-'+(time.getMonth()+1)+'-'+time.getFullYear()+" "+time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds();
    const  update_at = create_at;

    const product = new ProductModel({ name, price,description , create_at, update_at});
    await product.save();
    const productList = await ProductModel.find().lean();
    res.status(200).json(productList);
  } catch (err) {
    res.status(500).json({"success": 0,"message": "Oops! An error occurred."});
  }
});

// Định nghĩa API sửa đổi sản phẩm
app.put('/product/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const { name, price, description } = req.body;
    const time = new Date;
    const update_at = time.getDate()+'-'+(time.getMonth()+1)+'-'+time.getFullYear()+" "+time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds();
    const product = await ProductModel.findByIdAndUpdate(
      id,
      { name, price, description, update_at },
      { new: true }
    );
    const productList = await ProductModel.find().lean();
    res.status(200).json(productList);
  } catch (err) {
    res.status(500).json({"success": 0,"message": "No product found"});
  }
});

// Định nghĩa API xóa sản phẩm
app.delete('/product/:id', async (req, res) => {
  try {
    const { id } = req.params;
    await ProductModel.findByIdAndRemove(id);
    const productList = await ProductModel.find().lean();
    res.status(200).json(productList);
  } catch (err) {
    res.status(500).json({"success": 0,"message": "No product found"});
  }
});

// Khởi động server
const port = 8000;
app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});