import json
import matplotlib.pyplot as plt
import numpy as np

def load_data(filename):
    with open(filename, 'r') as f:
        return json.load(f)

def plot_execution_time(data):
    graph_names = []
    prim_times = []
    kruskal_times = []
    
    for result in data['results']:
        graph_names.append(result['graphName'])
        prim_times.append(result['prim']['executionTime'])
        kruskal_times.append(result['kruskal']['executionTime'])
    
    x = np.arange(len(graph_names))
    width = 0.35
    
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.bar(x - width/2, prim_times, width, label='Prim', color='skyblue')
    ax.bar(x + width/2, kruskal_times, width, label='Kruskal', color='lightcoral')
    
    ax.set_xlabel('Graph Size')
    ax.set_ylabel('Execution Time (ms)')
    ax.set_title('Comparison of Execution Time: Prim vs Kruskal')
    ax.set_xticks(x)
    ax.set_xticklabels(graph_names, rotation=45, ha='right')
    ax.legend()
    ax.grid(axis='y', alpha=0.3)
    
    plt.tight_layout()
    plt.savefig('execution_time_comparison.png', dpi=300, bbox_inches='tight')
    print("Saved execution_time_comparison.png")
    plt.close()

def plot_operations(data):
    graph_names = []
    prim_ops = []
    kruskal_ops = []
    
    for result in data['results']:
        graph_names.append(result['graphName'])
        prim_ops.append(result['prim']['operations'])
        kruskal_ops.append(result['kruskal']['operations'])
    
    x = np.arange(len(graph_names))
    width = 0.35
    
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.bar(x - width/2, prim_ops, width, label='Prim', color='lightgreen')
    ax.bar(x + width/2, kruskal_ops, width, label='Kruskal', color='orange')
    
    ax.set_xlabel('Graph Size')
    ax.set_ylabel('Number of Operations')
    ax.set_title('Comparison of Operations: Prim vs Kruskal')
    ax.set_xticks(x)
    ax.set_xticklabels(graph_names, rotation=45, ha='right')
    ax.legend()
    ax.grid(axis='y', alpha=0.3)
    
    plt.tight_layout()
    plt.savefig('operations_comparison.png', dpi=300, bbox_inches='tight')
    print("Saved operations_comparison.png")
    plt.close()

def plot_scalability(data):
    vertices_counts = []
    prim_times = []
    kruskal_times = []
    
    for result in data['results']:
        vertices_counts.append(result['vertices'])
        prim_times.append(result['prim']['executionTime'])
        kruskal_times.append(result['kruskal']['executionTime'])
    
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.plot(vertices_counts, prim_times, marker='o', label='Prim', linewidth=2, markersize=8)
    ax.plot(vertices_counts, kruskal_times, marker='s', label='Kruskal', linewidth=2, markersize=8)
    
    ax.set_xlabel('Number of Vertices')
    ax.set_ylabel('Execution Time (ms)')
    ax.set_title('Scalability Analysis: Execution Time vs Graph Size')
    ax.legend()
    ax.grid(True, alpha=0.3)
    
    plt.tight_layout()
    plt.savefig('scalability_analysis.png', dpi=300, bbox_inches='tight')
    print("Saved scalability_analysis.png")
    plt.close()

def plot_cost_comparison(data):
    graph_names = []
    costs = []
    
    for result in data['results']:
        graph_names.append(result['graphName'])
        costs.append(result['prim']['totalCost'])
    
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.bar(graph_names, costs, color='steelblue')
    
    ax.set_xlabel('Graph')
    ax.set_ylabel('MST Total Cost')
    ax.set_title('Minimum Spanning Tree Total Cost')
    ax.set_xticklabels(graph_names, rotation=45, ha='right')
    ax.grid(axis='y', alpha=0.3)
    
    plt.tight_layout()
    plt.savefig('mst_cost.png', dpi=300, bbox_inches='tight')
    print("Saved mst_cost.png")
    plt.close()

def plot_comparison_matrix(data):
    fig, axes = plt.subplots(2, 2, figsize=(14, 12))
    
    graph_names = [r['graphName'] for r in data['results']]
    prim_times = [r['prim']['executionTime'] for r in data['results']]
    kruskal_times = [r['kruskal']['executionTime'] for r in data['results']]
    prim_ops = [r['prim']['operations'] for r in data['results']]
    kruskal_ops = [r['kruskal']['operations'] for r in data['results']]
    
    x = np.arange(len(graph_names))
    
    # Plot 1: Execution Time
    axes[0, 0].bar(x - 0.2, prim_times, 0.4, label='Prim', color='skyblue')
    axes[0, 0].bar(x + 0.2, kruskal_times, 0.4, label='Kruskal', color='lightcoral')
    axes[0, 0].set_xlabel('Graph')
    axes[0, 0].set_ylabel('Time (ms)')
    axes[0, 0].set_title('Execution Time')
    axes[0, 0].set_xticks(x)
    axes[0, 0].set_xticklabels(graph_names, rotation=45, ha='right')
    axes[0, 0].legend()
    axes[0, 0].grid(alpha=0.3)
    
    # Plot 2: Operations
    axes[0, 1].bar(x - 0.2, prim_ops, 0.4, label='Prim', color='lightgreen')
    axes[0, 1].bar(x + 0.2, kruskal_ops, 0.4, label='Kruskal', color='orange')
    axes[0, 1].set_xlabel('Graph')
    axes[0, 1].set_ylabel('Operations')
    axes[0, 1].set_title('Operation Count')
    axes[0, 1].set_xticks(x)
    axes[0, 1].set_xticklabels(graph_names, rotation=45, ha='right')
    axes[0, 1].legend()
    axes[0, 1].grid(alpha=0.3)
    
    # Plot 3: Cost
    costs = [r['prim']['totalCost'] for r in data['results']]
    axes[1, 0].bar(graph_names, costs, color='steelblue')
    axes[1, 0].set_xlabel('Graph')
    axes[1, 0].set_ylabel('MST Cost')
    axes[1, 0].set_title('MST Total Cost')
    axes[1, 0].set_xticklabels(graph_names, rotation=45, ha='right')
    axes[1, 0].grid(alpha=0.3)
    
    # Plot 4: Performance Ratio
    ratios = [kruskal_times[i] / prim_times[i] if prim_times[i] > 0 else 1.0 for i in range(len(prim_times))]
    axes[1, 1].plot(x, ratios, marker='o', linewidth=2, markersize=8)
    axes[1, 1].axhline(y=1, color='r', linestyle='--', label='Equal Performance')
    axes[1, 1].set_xlabel('Graph')
    axes[1, 1].set_ylabel('Time Ratio (Kruskal/Prim)')
    axes[1, 1].set_title('Performance Ratio')
    axes[1, 1].set_xticks(x)
    axes[1, 1].set_xticklabels(graph_names, rotation=45, ha='right')
    axes[1, 1].legend()
    axes[1, 1].grid(alpha=0.3)
    
    plt.tight_layout()
    plt.savefig('comparison_matrix.png', dpi=300, bbox_inches='tight')
    print("Saved comparison_matrix.png")
    plt.close()

def main():
    try:
        data = load_data('output.json')
        
        print("Generating plots...")
        plot_execution_time(data)
        plot_operations(data)
        plot_scalability(data)
        plot_cost_comparison(data)
        plot_comparison_matrix(data)
        
        print("\nAll plots generated successfully!")
        
    except FileNotFoundError:
        print("Error: output.json not found. Please run the Java program first.")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()


