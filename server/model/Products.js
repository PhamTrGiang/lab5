const mongoose = require('mongoose');
const ProductSchema = new mongoose.Schema({
    name: {
        type: String,
        require: true
    },
    price: {
        type: Number,
        require: true
    },
    description: {
        type: String,
    },
    created_at: {
        type: String,
        require: true
    },
    update_at: {
        type: String,
        require: true
    },
})

const ProductModel = new mongoose.model('product',ProductSchema);
module.exports = ProductModel;